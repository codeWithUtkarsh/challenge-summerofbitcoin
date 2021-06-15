package com.challenge.sob.model;

import java.util.List;

public class MempoolTransaction {

	private String txId;
	private Integer fee;
	private Integer weight;
	private List<String> parentTxId;
	public MempoolTransaction() {
		super();
	}
	public String getTxId() {
		return txId;
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public List<String> getParentTxId() {
		return parentTxId;
	}
	public void setParentTxId(List<String> parentTxId) {
		this.parentTxId = parentTxId;
	}
}
