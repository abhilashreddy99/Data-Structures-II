package Query6;

import java.util.ArrayList;

public class BlocksOfNFT {
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

	@Override
	public String toString() {
		return nft + "(Slope = " + slope + ")" +"(frequency = " + blocks.size() + ")" + " Suggestable for buyer (" + this.isSuggestable + ")"  +"\n" + this.getBlockString();
	}
}
