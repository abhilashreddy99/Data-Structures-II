package Query6;

public class Block{
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
