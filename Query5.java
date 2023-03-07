package com.osu.ds2;

import java.io.File;

import java.io.FileReader;
import java.io.FilenameFilter;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;

public class Query5 {
    static String folderpath = "./";

    String txnHash;
    String unixTimes;
    String dateTime;
    String action;
    String buyer;
    String nft;
    String tokenId;
    String type;
    String quantity;
    String price;
    String market;
    int unique_nftval;
    int freq;

    public Query5(String txnHash, String unixTimes, String dateTime, String action, String buyer, String nft,
            String tokenId,
            String type, String quantity, String price, String market) {
        this.txnHash = txnHash;
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

    public String getTxnHash() {
        return txnHash;
    }

    public void setTxnHash(String txnHash) {
        this.txnHash = txnHash;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public void setUnique_nftval(int unique_nftval) {
        this.unique_nftval = unique_nftval;
    }

    public int getUnique_nftval() {
        return unique_nftval;
    }

    public void setfreq(int freq) {
        this.freq = freq;
    }

    public int getfreq() {
        return freq;
    }

    @Override
    public String toString() {
        return "Buyer: [Txn hash= " + txnHash + ", Date Time = " + dateTime + ", NFT= " + nft + ", Type= " + type
                + ", Quantity= " + quantity + ", Price= " + price + "]";

    }

    public static void merge(Query5[] list, int left, int mid, int right) {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;
        // Create temp arrays 

        Query5 leftArray[] = new Query5[n1];
        Query5 rightArray[] = new Query5[n2];
        //Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = list[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = list[mid + 1 + j];
        }

        // Initial indexes of first and second subarrays

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            //comparing unique NFT values
            if (leftArray[i].getUnique_nftval() > rightArray[j].getUnique_nftval()) {
                list[k] = leftArray[i];
                i++;
            } else if (leftArray[i].getUnique_nftval() < rightArray[j].getUnique_nftval()) {

                list[k] = rightArray[j];
                j++;
            } else {

                //if the unique NFT values are matchhing then compare the frequency of transactions
                
                
                if (leftArray[i].getfreq() >= rightArray[j].getfreq()) {
                    list[k] = leftArray[i];
                    i++;
                } else {

                    list[k] = rightArray[j];
                    j++;
                }

            }
            k++;
        }
        //Copy remaining elements of Left array
        while (i < n1) {
            list[k] = leftArray[i];
            k++;
            i++;
        }
        //Copy remaining elements of Right array if nay
        while (j < n2) {
            list[k] = rightArray[j];
            k++;
            j++;
        }
    }

    public static void mergesort(Query5[] list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergesort(list, left, mid);
            mergesort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }

    static void datapoints(ArrayList<Query5> datapoint) {

        long totalTime = 0;
        //for 100 iteration to get the average of 100 iterations
        for (int count = 0; count < 100; count++) {

            Query5 blocks[] = new Query5[datapoint.size()];
            blocks = datapoint.toArray(blocks);
            long startOfSorting = System.nanoTime();
            //calling merge sort
            mergesort(blocks, 0, blocks.length - 1);
            long endOfSorting = System.nanoTime();
            long sumTotalTime = endOfSorting - startOfSorting;
            totalTime = sumTotalTime + totalTime;

        }
        //calculating the average of run times in 100 runs
        double avg = totalTime / 100.0;
        System.out.println(avg / 1000000.0 + "," + avg);

    }

    public static void main(String[] args) {
        try {
            File f = new File(folderpath);
            //printing out in the OutPutStream
            File Outputstrem = new File("outputStream.txt");
            PrintStream printoutput = new PrintStream(Outputstrem);
            System.setOut(printoutput);
            //this filters the file which ends with .csv in folderpath

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return name.endsWith(".csv");
                }
            };
            //here filter will apply
            String[] pathnames = f.list(filter);

            ArrayList<Query5> list_data = new ArrayList<Query5>();
            //hashmap of hashset to store the buyer with unique nfts they brought
            HashMap<String, HashSet<String>> count = new HashMap<>();
            HashSet<String> hs = new HashSet<>();
            //hashmap to store the buyer with number of transactione each buyer made
            HashMap<String, Integer> freqTnx = new HashMap<>();

            for (String pathname : pathnames) {
                String[] nextData;
                

                FileReader filereader = new FileReader(pathname);
                // create csvReader object passing
                // file reader as a parameter
                CSVReader csvReader = new CSVReader(filereader);

                // we are going to read data line by line
                // we remove those data with invalid price field
                 nextData = csvReader.readNext();

                

                // we are going to read records line by line
                // we remove those with invalid price field
                nextData = csvReader.readNext();

                long readRecords = 0;
                while ((nextData = csvReader.readNext()) != null) {
                    // we ignore those transactions which buyer is not available
                    if (nextData[9] == "") {
                        readRecords++;
                        continue;
                    }
                    if (nextData[9].indexOf("$") == -1) {
                        if (nextData[9].indexOf("GALA") != -1) {
                            nextData[9] = nextData[9].split("GALA")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 0.03604);
                        } else if (nextData[9].indexOf("WETH") != -1) {
                            nextData[9] = nextData[9].split("WETH")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 1322.16);
                        } else if (nextData[9].indexOf("ASH") != -1) {
                            nextData[9] = nextData[9].split("ASH")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 0.9406);
                        } else if (nextData[9].indexOf("TATR") != -1) {
                            nextData[9] = nextData[9].split("TATR")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 0.012056);
                        } else if (nextData[9].indexOf("USDC") != -1) {
                            nextData[9] = nextData[9].split("USDC")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 1.00);
                        } else if (nextData[9].indexOf("MANA") != -1) {
                            nextData[9] = nextData[9].split("MANA")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 0.64205);
                        } else if (nextData[9].indexOf("SAND") != -1) {
                            nextData[9] = nextData[9].split("SAND")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 0.7919);
                        } else if (nextData[9].indexOf("RARI") != -1) {
                            nextData[9] = nextData[9].split("RARI")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 2.18);
                        } else if (nextData[9].indexOf("CTZN") != -1) {
                            nextData[9] = nextData[9].split("CTZN")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 0.00321);
                        } else if (nextData[9].indexOf("APE") != -1) {
                            nextData[9] = nextData[9].split("APE")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 4.62);
                        } else if (nextData[9].indexOf("ETH") != -1) {
                            nextData[9] = nextData[9].split("ETH")[0].trim();
                            nextData[9] = nextData[9].replace(",", "");
                            nextData[9] = Double.toString(Double.valueOf(nextData[9]) * 1309.97);
                        } else {
                            readRecords++;
                            continue;
                        }
                    } else {
                        nextData[9] = nextData[9].split("[$]")[1].replace(")", "");
                        nextData[9] = nextData[9].replace(",", "");
                    }
                    Query5 b = new Query5(nextData[0], nextData[1], nextData[2], nextData[3], nextData[4],
                            nextData[5], nextData[6], nextData[7], nextData[8],
                            nextData[9], nextData[10]);
                            //if the buyer is alreday in hashmap just add the nft to that buyer
                            //hashmap will not allow duplicate values here

                    if (count.containsKey(b.getBuyer())) {
                        count.get(b.getBuyer()).add(b.getNft());
                    } else {
                        //if the buyer occurs for the first time adding those buyer and NFT to the hashmap
                        hs = new HashSet<>();
                        hs.add(b.getNft());
                        count.put(b.getBuyer(), hs);
                    }
                    //adding buyer details to the list
                    
                    list_data.add(b);
                    //for tranasaction frequency
                    //if buyer is already in the hashmap then increment the frequency of the buyer  to 1

                    if (freqTnx.containsKey(b.getBuyer())) {
                        freqTnx.put(b.getBuyer(), freqTnx.get(b.getBuyer()) + 1);
                    } else {
                        //if the busyer comes for the first time then put 1 for frequency of that buyer
                        freqTnx.put(b.getBuyer(), 1);

                    }
                    readRecords++;
                    //calling the function to find the data points for graph

                    if (readRecords % 1000 == 0) {
                        // datapoints(list_data);
                    }

                }

            }
            //adding the size of different nfts to each buyer and transaction frequency of each buyer to original data
            for (Query5 nft : list_data) {
                int a = count.get(nft.getBuyer()).size();
                nft.setUnique_nftval(a);

                int count_1 = freqTnx.get(nft.getBuyer());
                nft.setfreq(count_1);
            }
            //converting arraylist to array

            Query5 blocks[] = new Query5[list_data.size()];
            blocks = list_data.toArray(blocks);

            long startTime = System.nanoTime();
            //calling mergesort to sort down buyer with different NFTs he brought when there is tie
            //then sort by transaction frequency

            mergesort(blocks, 0, blocks.length - 1);

            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            //printing the run time for sorting
            System.out.println("Time taken to sort the data is " + totalTime / 1000000.0
                    + " milliseconds which is " + totalTime + " nanoseconds");


            //print the sorted data by looping through the blocks
            for (Query5 temp : blocks) {

                System.out.println(temp.getBuyer() + " " + "(NFTfrequency = " + temp.getUnique_nftval() + ","
                        + "Txnfrequency = " + temp.getfreq() + ") \n"

                        + temp.toString() + "\n");

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}