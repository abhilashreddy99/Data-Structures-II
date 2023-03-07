package Query6;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import com.opencsv.CSVReader;
import java.text.SimpleDateFormat;  
import java.util.Date;


class Block{
	private String txtHash;
	private String unixTimes;
	private String dateTime;
	private String action;
	private String buyer;
	private String nft;
	private String tokenId;
	private int type;
	private int quantity;
	private Double price;
	private String market;
	
	public Block(String txtHash, String unixTimes, String dateTime, String action, String buyer, String nft, String tokenId,
			int type, int quantity, Double price, String market) {		
		this.txtHash = txtHash;
		this.unixTimes = unixTimes;
		this.dateTime = dateTime;
		this.action = action;
		this.buyer = buyer;
		this.nft = nft;
		this.tokenId = tokenId;
		this.type = type;
		this.quantity = quantity;
		this.price = price;
		this.market = market;
	}
	
	public String getTxtHash() {
		return txtHash;
	}
	public void setTxtHash(String txtHash) {
		this.txtHash = txtHash;
	}
	public String getUnixTimes() {
		return unixTimes;
	}
	public void setUnixTimes(String unixTimes) {
		this.unixTimes = unixTimes;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getNft() {
		return nft;
	}
	public void setNft(String nft) {
		this.nft = nft;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}

	@Override
	public String toString() {
		return "NFT: [Txn hash=" + txtHash + ", Date Time (UTC)" + dateTime +", NFT=" + nft +", Buyer" +this.buyer + ", Type=" + type
				+ ", Quantity=" + quantity + ", Price=" + price + "] \n";
	}

}

class BlocksOfNFT {
	private String nft;
	
	private ArrayList<Block> blocks; 

	private Double slope;

	private boolean isSuggestable;

	public BlocksOfNFT(String nft, ArrayList<Block> blocks) {
		super();
		this.nft = nft;
		this.blocks = blocks;
		this.isSuggestable = false;
	}

	public String getNft() {
		return nft;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> arr){
		this.blocks = arr;
	}

	public void setSuggestion(boolean val){
		this.isSuggestable = val;
	}

	public void appendBlock(Block block) {
		this.blocks.add(block);
	}

	public String getBlockString(){
		StringBuilder sb = new StringBuilder();

		for(int i=0;i< this.blocks.size();i++){
			sb.append(this.blocks.get(i).toString());
		}
		return sb.toString();
	}

	public boolean getSuggestion() {
		return this.isSuggestable;
	}

	public void setSlope(Double val){
		this.slope = val;
	}

	public Double getSlope() {
		return slope;
	}

	public boolean canSuggest(){
		return this.getSlope() > 0;
	}

	@Override
	public String toString() {
		return nft + "(Slope = " + slope + ")" +"(frequency = " + blocks.size() + ")" + " Suggestable for buyer (" + this.canSuggest() + ")"  +"\n" + this.getBlockString();
	}
}



class LinearRegression {
    private final Double intercept, slope;
    private final Double r2;
    private final Double svar0, svar1;

    public LinearRegression(Double[] x, Double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("x and y should be equal");
        }
        int n = x.length;

        //iterating through array once
        Double sumx = 0.0;
		Double sumy = 0.0;
		Double sumx2 = 0.0;

        for (int iter = 0; iter < n; iter++) {
            sumx  += x[iter];
            sumx2 += x[iter]*x[iter];
            sumy  += y[iter];
        }

        Double xbar = sumx / n;
        Double ybar = sumy / n;

        //Seconds iteration
        Double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int iter = 0; iter < n; iter++) {
            xxbar += (x[iter] - xbar) * (x[iter] - xbar);
            yybar += (y[iter] - ybar) * (y[iter] - ybar);
            xybar += (x[iter] - xbar) * (y[iter] - ybar);
        }
        slope  = xybar / xxbar;
        intercept = ybar - slope * xbar;

        Double rss = 0.0;      //  sum of squares residues
        Double ssr = 0.0;      //  sum of squares regression
        for (int iter = 0; iter < n; iter++) {
            Double fit = slope*x[iter] + intercept;
            rss += (fit - y[iter]) * (fit - y[iter]);
            ssr += (fit - ybar) * (fit - ybar);
        }

        int degreesOfFreedom = n-2;
        r2    = ssr / yybar;
        Double svar  = rss / degreesOfFreedom;
        svar1 = svar / xxbar;
        svar0 = svar/n + xbar*xbar*svar1;
    }

    public double slope() {
        return slope;
    }
}



class SortbyTime implements Comparator<Block>
{
    public int compare(Block a, Block b)
    {

		try{
			Date date1 = Query6.parseDate(a.getDateTime());  
			Date date2 = Query6.parseDate(b.getDateTime()); 

			return date1.compareTo(date2);
		}
		catch(Exception e){
			return 0;
		}
    }
}

@SuppressWarnings("deprecation")
public class Query6 {
	
	static void sortNFTBlock(ArrayList<Block> arr){
		Collections.sort(arr, new SortbyTime());
	}

	static Date parseDate(String dateString) {
		String[] patterns = new String[] { "dd/mm/yy hh:mm", "dd-mm-yyyy hh:mm" };
        Date date = null;
        for (String pattern : patterns) {
            date = parseTimeBaseOnPattern(pattern, dateString);
            if (date != null) {
                break;
            }
        }
        if (date == null) {
            date = new Date();
        }
        return date;
    }

	public static Date parseTimeBaseOnPattern(String pattern, String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date date = formatter.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }
    }


	static void sortNFTsByTime(BlocksOfNFT[] arr){
		int n = arr.length;
		for(int i=0; i<n; i++){
			ArrayList<Block> blocks = arr[i].getBlocks();
			sortNFTBlock(blocks);
			arr[i].setBlocks(blocks);
		}
	}

	static void getSlopesForNFT(BlocksOfNFT[] arr){
		int n = arr.length;
		for(int i=0; i<n; i++){
			HashMap<String, ArrayList<Double>> dayPrices = new HashMap<>();

			ArrayList<Block> blocks = arr[i].getBlocks();
			for(Block cur:blocks){
				Date curBlockDate = parseDate(cur.getDateTime());
				String dayString =  Integer.toString(curBlockDate.getYear()) + Integer.toString(curBlockDate.getMonth()) + Integer.toString(curBlockDate.getDate());
				if(dayPrices.containsKey(dayString)){
					ArrayList<Double> prev = dayPrices.get(dayString);
					prev.add(cur.getPrice());
					dayPrices.put(dayString, prev);
				}
				else {
					ArrayList<Double> prev = new ArrayList<>();
					prev.add(cur.getPrice());
					dayPrices.put(dayString, prev);
				}
			}

			HashMap<String, Double> dayAvgHm = new HashMap<>();
			ArrayList<Double> xPoints = new ArrayList<>();
			for(String key: dayPrices.keySet()){
				ArrayList<Double> pricesArr = dayPrices.get(key);
				Double sum = 0.0;
				for(Double price: pricesArr){
					sum = sum + price;
				}
				Double avg = sum / pricesArr.size();
				dayAvgHm.put(key, avg);
			}

			for(String key: dayAvgHm.keySet()){
				Double val = dayAvgHm.get(key);
				xPoints.add(val);
			}

			if(xPoints.size() < 2){
				arr[i].setSlope(0.0);
				continue;
			}
			Double xArr[] = new Double[xPoints.size()];
			xArr = xPoints.toArray(xArr);
			Double yArr[] = new Double[xPoints.size()];
			for(int k=0;k<yArr.length;k++){
				yArr[k] = k * 1.0;
			}

			LinearRegression lr  = new LinearRegression(xArr, yArr);

			arr[i].setSlope(lr.slope());
			arr[i].setBlocks(blocks);
		}

	}

	public static void merge(BlocksOfNFT list[], int left, int mid, int right) {
		int n1 = mid - left + 1;
		int n2 = right - mid;

		BlocksOfNFT leftArray[] = new BlocksOfNFT[n1];
		BlocksOfNFT rightArray[] = new BlocksOfNFT[n2];
		for (int i = 0; i < n1; i++) {
			leftArray[i] = list[left + i];
		}
		for (int j = 0; j < n2; j++) {
			rightArray[j] = list[mid + 1 + j];
		}

		int i = 0, j = 0, k = left;
		while (i < n1 && j < n2) {
			if (leftArray[i].getSlope() >= rightArray[j].getSlope()) {
				list[k] = leftArray[i];
				i++;
			} else {
				list[k] = rightArray[j];
				j++;
			}
			k++;
		}
		while (i < n1) {
			list[k] = leftArray[i];
			k++;
			i++;
		}
		while (j < n2) {
			list[k] = rightArray[j];
			k++;
			j++;
		}
	}

	public static void mergesort(BlocksOfNFT list[], int left, int right) {
		if (left < right) {
			int mid = left + (right - left) / 2;
			mergesort(list, left, mid);
			mergesort(list, mid + 1, right);
			merge(list, left, mid, right);
		}
	}

	static void swap(BlocksOfNFT[] arr, int i, int j) {
		BlocksOfNFT temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}



	public static void main(String args[]) {

		// check invalid command line arguments
		if (args.length < 1) {
			System.out.println("Invalid command line arguments: need one input file argument");
			return;
		} else if (args.length > 1) {
			System.out.println("Invalid command line arguments: you gave more than one argument");
			return;
		}

		try {
			PrintStream out = new PrintStream(new FileOutputStream("output_q.txt"));
            System.setOut(out);

			FileReader filereader = new FileReader(args[0]);
			// create csvReader object passing
			// file reader as a parameter
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;

			HashMap<String, BlocksOfNFT> nftBlockMap = new HashMap<>();
			HashMap<String, ArrayList<Block>> hmNFTBuyerTimeHash = new HashMap<String, ArrayList<Block>>();

			// HashMap<Integer, HashMap<String, Integer>> buyerNumTokenMap = new
			// HashMap<>();

			// we are going to read data line by line
			// we remove those data with invalid price field
			nextRecord = csvReader.readNext();
			int count = 0;
			while ((nextRecord = csvReader.readNext()) != null) {
				if (nextRecord[9].indexOf("$") == -1) {
					if (nextRecord[9].indexOf("GALA") != -1) {
						nextRecord[9] = nextRecord[9].split("GALA")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 0.03604);
					} else if (nextRecord[9].indexOf("WETH") != -1) {
						nextRecord[9] = nextRecord[9].split("WETH")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 1322.16);
					} else if (nextRecord[9].indexOf("ASH") != -1) {
						nextRecord[9] = nextRecord[9].split("ASH")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 0.9406);
					} else if (nextRecord[9].indexOf("TATR") != -1) {
						nextRecord[9] = nextRecord[9].split("TATR")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 0.012056);
					} else if (nextRecord[9].indexOf("USDC") != -1) {
						nextRecord[9] = nextRecord[9].split("USDC")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 1.00);
					} else if (nextRecord[9].indexOf("MANA") != -1) {
						nextRecord[9] = nextRecord[9].split("MANA")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 0.64205);
					} else if (nextRecord[9].indexOf("SAND") != -1) {
						nextRecord[9] = nextRecord[9].split("SAND")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 0.7919);
					} else if (nextRecord[9].indexOf("RARI") != -1) {
						nextRecord[9] = nextRecord[9].split("RARI")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 2.18);
					} else if (nextRecord[9].indexOf("CTZN") != -1) {
						nextRecord[9] = nextRecord[9].split("CTZN")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 0.00321);
					} else if (nextRecord[9].indexOf("APE") != -1) {
						nextRecord[9] = nextRecord[9].split("APE")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 4.62);
					} else if (nextRecord[9].indexOf("ETH") != -1) {
						nextRecord[9] = nextRecord[9].split("ETH")[0].trim();
						nextRecord[9] = nextRecord[9].replace(",", "");
						nextRecord[9] = Double.toString(Double.valueOf(nextRecord[9]) * 1309.97);
					} else {
						// there are some price with just number without units
						// System.out.println("others" + nextRecord[9]);
						continue;
					}
				} else {
					nextRecord[9] = nextRecord[9].split("[$]")[1].replace(")", "");
					nextRecord[9] = nextRecord[9].replace(",", "");
				}
				Block b = new Block(nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3], nextRecord[4],
						nextRecord[5], nextRecord[6], Integer.parseInt(nextRecord[7]), Integer.parseInt(nextRecord[8]),
						Double.valueOf(nextRecord[9]), nextRecord[10]);

				BlocksOfNFT curNftBlock = nftBlockMap.get(b.getNft());

				ArrayList<Block>  curNFTBuyerTimeHash = hmNFTBuyerTimeHash.get(new String(b.getNft() +b.getBuyer() + b.getDateTime()));

				if(curNFTBuyerTimeHash == null){
					count++;
					ArrayList<Block> blocks = new ArrayList<Block>();
					blocks.add(b);
					hmNFTBuyerTimeHash.put(new String(b.getNft() + b.getBuyer() +b.getDateTime()), blocks);
				}
				else {
					curNFTBuyerTimeHash.add(b);
				}

				// first time of seeing this buyer
				if (curNftBlock == null) {
					// only one block in blocks
					ArrayList<Block> blocks = new ArrayList<Block>();
					blocks.add(b);

					BlocksOfNFT newBuyerBlock = new BlocksOfNFT(b.getNft(), blocks);

					nftBlockMap.put(b.getNft(), newBuyerBlock);
				} else {
					curNftBlock.appendBlock(b);
				}
			}
			System.out.println("End of parsing data into our data structrue");

			StringBuilder sb = new StringBuilder();

			ArrayList<BlocksOfNFT> list = new ArrayList<>(nftBlockMap.values());
			for(ArrayList<Block> vals:hmNFTBuyerTimeHash.values()){
				boolean diffPrice = false;
				if(vals.size() < 2){
					continue;
				}
				for(int i=1;i<vals.size();i++){
					if(!vals.get(i).getPrice().equals(vals.get(i-1).getPrice())){
						diffPrice = true;
						break;
					}
				}
			}

			BlocksOfNFT blocks[] = new BlocksOfNFT[list.size()];
			blocks = list.toArray(new BlocksOfNFT[list.size()]);
			sortNFTsByTime(blocks);

			getSlopesForNFT(blocks);
			long startTime = System.nanoTime();

			mergesort(blocks,0, blocks.length-1);

			for (int i = 0; i < blocks.length; i++) {
				// if(blocks[i].getSlope() != 0.0){
					System.out.println(blocks[i].toString());
				// }
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
