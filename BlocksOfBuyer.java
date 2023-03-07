package Query3;

import java.util.ArrayList;

public class BlocksOfBuyer {
	private String buyer;
	
	private ArrayList<Block> blocks; 
	private ArrayList<String> transactions; 

	public BlocksOfBuyer(String buyer, ArrayList<Block> blocks, ArrayList<String> transactions) {
		super();
		this.buyer = buyer;
		this.blocks = blocks;
		this.transactions = transactions;
	}

	public String getBuyer() {
		return buyer;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void appendBlock(Block block) {
		this.blocks.add(block);
	}


	public void appendTransaction(String transaction) {
		this.transactions.add(transaction);
	}
	
	public int getNumOfTransactions() {
		return this.transactions.size();
	}

	public String getBlockString(){
		StringBuilder sb = new StringBuilder();

		for(int i=0;i< this.blocks.size();i++){
			sb.append(this.blocks.get(i).toString());
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return buyer+"(frequency = " + transactions.size() + ")\n" + this.getBlockString();
	}
}
