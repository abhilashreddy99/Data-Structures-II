package Query4;

import java.util.ArrayList;

public class BlocksOfToken {
	private String tokenId;
	
	private ArrayList<Block> blocks; 
	//distinct buyers
	private ArrayList<String> buyers; 

	public BlocksOfToken(String tokenId, ArrayList<Block> blocks, ArrayList<String> buyers) {
		super();
		this.tokenId = tokenId;
		this.blocks = blocks;
		this.buyers = buyers;
	}

	public String getTokenId() {
		return tokenId;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void appendBlock(Block block) {
		this.blocks.add(block);
	}


	public void appendBuyer(String buyer) {
		this.buyers.add(buyer);
	}
	
	public boolean notContainSameBuyer(String buyer) {
		return !this.buyers.contains(buyer);
	}
	
	public int getNumOfBuyers() {
		return this.buyers.size();
	}

	
}
