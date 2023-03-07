package Query4;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.opencsv.CSVReader;


//Code for query 4
//Team 5
//Dingyi Kang and Rohith Alamuri

public class Query4 {
	
	// Sort down “Token IDs” by the number of different buyers
	// Output: Token hash (number of different buyers = ?)
	// Token ID: Txn hash, Date Time (UTC), Buyer, NFT, Type, Quantity, Price
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
			//create a FileReader object to try to read the file in the passed path
			FileReader filereader = new FileReader(args[0]);
			// create csvReader object and pass the file reader as a parameter
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;
			
			//This HashMap is used to associate a tokenId with a data structure associated with that tokenId. The data structure BlocksOfToken is used to store all blocks/records of a certain tokenId. Also, It stores all different buyers of a tokenId
			HashMap<String, BlocksOfToken> tokenBlockMap = new HashMap<>();
			
			//This outer HashMap is used to associated a number of different buyers with all tokenIds with that amount of different buyers
			//The inner HashMao is used to associated a tokenId with an integer (the value of integer doesn't matter). If using a tokenId as a key in HashMap and get a non-null value, it means that tokenId exist in the hashMap and thus the tokenId belongs to the category of tokenIds with that certain amount of different buyers (which is the value of the key of the outer HashMap)
			HashMap<Integer, HashMap<String, Integer>> buyerNumTokenMap = new HashMap<>();
			
			// we are going to read data line by line
			// we remove those data with invalid price field
			nextRecord = csvReader.readNext();
			int invalidBuyerNum = 0;
			while ((nextRecord = csvReader.readNext()) != null) {
				//we ignore those transactions which buyer is not available
				if (nextRecord[9] == "") {
					invalidBuyerNum += 1;
					continue;
				}
				//we convert those field which is not in $ unit to $ unit
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
						// there are some price with just number without units. We ignore them
						System.out.println("others" + nextRecord[9]);
						continue;
					}
				} else {
					//parse the price field and get only the magnitude value of the price
					nextRecord[9] = nextRecord[9].split("[$]")[1].replace(")", "");
					nextRecord[9] = nextRecord[9].replace(",", "");
				}
				
				// store each entry/record in a data structure, Block
				Block b = new Block(nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3], nextRecord[4],
						nextRecord[5], nextRecord[6], Integer.parseInt(nextRecord[7]), Integer.parseInt(nextRecord[8]),
						Double.valueOf(nextRecord[9]), nextRecord[10]);
				//BlocksOfToken is data structure is used to store all blocks/records of a certain tokenId. Also, It stores all different buyers of a tokenId
				
		// *** time complexity of retrieving a value from hashMap blksOfTkn -- range from constant time to O(log n) to get blocksOfToken from hashMap *** 
				// given collision may happen in the HashMap blksOfTkn, the worst case is O(log n)
				// Reason of O(log n): "From Java 8 onwards, HashMap, ConcurrentHashMap, and LinkedHashMap will use the balanced tree in place of linked list to handle frequently hash collisions. The idea is to switch to the balanced tree once the number of items in a hash bucket grows beyond a certain threshold. This will improve the worst-case get() method performance from O(n) to O(log n)."
				BlocksOfToken blksOfTkn = tokenBlockMap.get(b.getTokenId());

				// first time of seeing this tokenId
				if (blksOfTkn == null) {
					// since this is the first time this tokenId appears, we initiate a block array and add this block into the block array in data structure of BlocksOfToken
					ArrayList<Block> blocks = new ArrayList<Block>();
					blocks.add(b);
					// since this is the first time this tokenId appear, we initiate a buyer array and add this buyerId in buyer array in the data structure of BlocksOfTokenonly
					ArrayList<String> buyers = new ArrayList<String>();
					buyers.add(b.getBuyer());
					//we construct a BlocksOfToken with the tokenId, block array and buyer array
					BlocksOfToken blocksOfToken = new BlocksOfToken(b.getTokenId(), blocks, buyers);
					//associate this tokenId with this BlockOfToken data structure
					tokenBlockMap.put(b.getTokenId(), blocksOfToken);
					
					//since this is the first time this tokenId appears, we add it into the hashMap between number of different buyers and tokenIds at the key of 1
					
					//if the hashMap doesn't have key of 1
		//*** time complexity of checking if a key exist in the outer hashMap -- constant time for sure since keys of buyerNumTokenMap is limited no collision of hashMap will happen***
					if (buyerNumTokenMap.get(1) == null) {
						//initiate a inner hashMap to indicate this tokenId exists within the outer hashMap
						HashMap<String, Integer> tokenIdsMap = new HashMap<String, Integer>();
						//any integer value is fine. doesn't matter here. Just if the value of the inner hashMap is not null, it indicates this tokenId exists within the outer hashMap
						tokenIdsMap.put(b.getTokenId(), 1);
						//given this is the first time this topenId is read, it has only one distinct buyer and thus stored at the key of 1 of the outer hashMap
						buyerNumTokenMap.put(1, tokenIdsMap);
					} else {
						//if the hashMap already has key of 1, directly stores the tokenId with an integer at the key of 1 of the outer hashMap
						//integer could be 1 or any value. The value doesn't matter here. Just if the value of the inner hashMap is not null, it indicates this tokenId exists within the outer hashMap
						buyerNumTokenMap.get(1).put(b.getTokenId(), 1);
					}
				} 
				// if a tokenId appears again. This is less frequent in practice
				// we add this block information into the blocksOfToken associated its tokenId
				else {
					// we first append the block/records into the array of blocks associated with this tokenId 
					blksOfTkn.appendBlock(b);
					
					// In the notContainSameBuyer function, we check if the buyer not already exist.
		// *** time complexity -- this contain check theoretically could be linear in worst case but can be viewed as constant in practice since number of different buyers of a tokenId is very small
					// If not exist yet, we append the buyer and update the buyerNumberBuyers
					if (blksOfTkn.notContainSameBuyer(b.getBuyer())) {
						// append this buyer and remove it from current buyerNumTokenMap and add it to the next
						
						// remove this tokenId pair from previous hashMap
						int currentNumberBuyers = blksOfTkn.getNumOfBuyers();
		// *** time complexity -- this removing could be most expensive. If there is collision in hashMap, the worst case of running time is O(lgn)***//
						buyerNumTokenMap.get(currentNumberBuyers).remove(b.getTokenId());
						//add them into the next key of the outer hashMap
						//if next key of the outer hashMap not exist yet, create a inner HashMap with a key-value pair of this tokenId and a arbitrary integer, and assign this hashMap to the value of the next integer key
						if (buyerNumTokenMap.get(currentNumberBuyers + 1) == null) {
							HashMap<String, Integer> tokenIdsMap = new HashMap<String, Integer>();
							tokenIdsMap.put(b.getTokenId(), 1);
							buyerNumTokenMap.put(currentNumberBuyers + 1, tokenIdsMap);
						} 
						//if next key of the outer hashMap already exists, directly add it at the key
						else {
							buyerNumTokenMap.get(currentNumberBuyers + 1).put(b.getTokenId(), 1);
						}
						//Given this buyer not exist in buyer array of the BlocksOfToken associated with this tokenId yet, we append it in the buyer array
						blksOfTkn.appendBuyer(b.getBuyer());
					}
				}
			}
			//close the CSV reader
			csvReader.close();
			System.out.println("invalid buyer: " + invalidBuyerNum);

		//*** time complexity -- in summary, it takes O(nlgn) to parse the data into the data structure. The lgn is due to search and removing key in hashMap when there is collision
			System.out.println("End of parsing data into our data structrue");
			
			//begin the timer
			long startTime = System.nanoTime();	
			
				//clear all entries with empty hashMap values in the outer hashMap, buyerNumTokenMap
				//Get the iterator over the HashMap first
		        Iterator<Map.Entry<Integer, HashMap<String, Integer>> >
		            iterator = buyerNumTokenMap.entrySet().iterator();
		        //Iterate over the HashMap
		        while (iterator.hasNext()) {
		            // Get the entry at this iteration
		            Map.Entry<Integer, HashMap<String, Integer>> entry = iterator.next();
		            // Check if its value, which is hashMap, is with size of 0
		            if (entry.getValue().size() == 0) {
		                //If it is empty hashMap, remove this entry from the outer HashMap
		                iterator.remove();
		            }
		        }
		        
		        //Given all keys in the outer hashMap are integer, the return keySet of the hashMap are different buyers numbers in ascending order
				//Hence, to get to a list of different buyer numbers in descending order, we just reversed order of the keySet of the hashMap and store them into an array
		        //then, we get a key list in descending order which we can use to access all transactions in the order of the descending order of the amount of different buyers of their tokenId
		//*** time complexity -- best case -- all records have same number of different buyer which means buyerNumTokenMap.size() is equal to 1. Running time is Omega(1)
		        // the worst case -- maximum distinct numbers of buyers for n blocks of data -- which is (1+2+3+4+...+k)=n  => k(k+1)=2n => k<sqrt(2n) => O(k) = O(sqrt(2n)) = O(sqrt(n))
				int[] buyerNums = new int[buyerNumTokenMap.size()];
				int index = 0;
				for (Integer element : buyerNumTokenMap.keySet()) {
					index++;
					buyerNums[buyerNumTokenMap.size()-index] = element.intValue();
				}
			
			//end the timer
			long endTime = System.nanoTime();
			long totalTime = endTime - startTime;
			System.out.println("Time taken to sort the data is " + totalTime / 1000000.0 + " milliseconds which is " + totalTime + " nanoseconds");
			
			System.out.println("Different buyer numbers: " + Arrays.toString(buyerNums));

			
			//initiate a fileWriter
			FileWriter myWriter = new FileWriter("query4_output.txt");
			for (int buyerNum : buyerNums) {
				System.out.println("************** There are " + buyerNumTokenMap.get(buyerNum).keySet().size() + " tokenIds with " + buyerNum + " different buyers **************");
				for (String tokenId : buyerNumTokenMap.get(buyerNum).keySet()) {

					ArrayList<Block> blocks = tokenBlockMap.get(tokenId).getBlocks();
				
					for (Block b : blocks) {
						//write the data into file
						myWriter.write(b.getTokenId()+" (number of different buyers = "+ buyerNum +")");
						myWriter.write(b.toString());
					}
				}
			}
			//close fileWriter
			myWriter.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
