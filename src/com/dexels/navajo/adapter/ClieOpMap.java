package com.dexels.navajo.adapter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.dexels.navajo.adapter.clieop.ClieOpPost;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class ClieOpMap implements Mappable {
	
	public ClieOpPost[] posts;
	public ArrayList draftPosts = null;
	
	public String filePreRecord;
	public String batchPreRecord;
	public String fixedDescriptionRecord;
	public String constituentRecord;
	public String batchCloseRecord;
	public String fileCloseRecord;
	
	public String clieOPId;
	public String accountNumber;
	public String currency;
	public String fixedDescription;
	public Date processingDate;
	public String processingDateStr;
	
	public Binary content;
	FileOutputStream out;
	ByteArrayOutputStream bos;
	PrintStream p;
	
	public ClieOpMap(){
		try {
			bos = new ByteArrayOutputStream();
			p = new PrintStream(bos);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void setPosts(ClieOpPost[] posts) {
		this.posts = posts;
	}
	
//	public int getPostCount(){
//		return posts.length;
//	}
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public Binary getContent() throws UserException {
		Binary b = null;
		generateClieOP();
		b = new Binary(bos.toByteArray());
		try {
			out = new FileOutputStream("clieop.txt");
			out.write(b.getData());
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage());
		}
		return b;
	}
	
	private void generateClieOP(){
		p.println(getFilePreRecord());
		p.println(getBatchPreRecord());
		p.println(getFixedDescriptionRecord());
		p.println(getConstituentRecord());
		if (draftPosts != null) {
			posts = new ClieOpPost[draftPosts.size()];
			posts = (ClieOpPost[]) draftPosts.toArray();
		}
		for(int i=0; i<posts.length; i++){
			p.println(posts[i].getTransactionRecord());
			p.println(posts[i].getPaymentCharacteristicRecord());
			p.println(posts[i].getDescriptionRecord());
			p.println(posts[i].getBenaficiaryNameRecord());
			p.println(posts[i].getBenaficiaryPlaceRecord());
		}
		p.println(getBatchCloseRecord());
		p.println(getFileCloseRecord());
		p.close();
	}
	
	public void store() throws MappableException, UserException {
		
	}
	
	/**
	 * TODO Remove hard coded KNVB reference.
	 * 
	 * @return
	 */
	public String getFilePreRecord(){
		Date date = new Date();
		SimpleDateFormat nowDateFormat = new SimpleDateFormat("ddMMyy");
		String fileDateOfMaking = null;
		try {
			fileDateOfMaking = nowDateFormat.format(date);
		} catch(Exception e){
			System.err.println(e);
		}		
		filePreRecord = "0001A"+fileDateOfMaking+"CLIEOP03KNVB2"+fileDateOfMaking.substring(0,2)+"011";
		return filePreRecord;
	}
	public String getBatchPreRecord(){
		batchPreRecord = "0010B"+accountNumber+"0001"+currency;
		return batchPreRecord;
	}
	public String getFixedDescriptionRecord() {
		fixedDescriptionRecord = "0020A"+fixedDescription;
		return fixedDescriptionRecord;
	}
	
	/**
	 * TODO Remove hard coded Betaald voetbal reference.
	 * 
	 * @return
	 */
	public String getConstituentRecord(){	
		constituentRecord = "0030B1"+processingDateStr+"BU Betaald Voetbal                 P";
		return constituentRecord;
	}
	public String getBatchCloseRecord(){
		int totalAmount = 0;
		for(int i=0;i<posts.length;i++){
			totalAmount += (int) Math.round(Double.parseDouble(posts[i].amount));
		}
		String totalAmountString = Integer.toString(totalAmount);
		if(totalAmountString.length()!=18){
			int loopSize = 18-totalAmountString.length();
			for(int i=0; i<loopSize; i++){
				totalAmountString ="0"+totalAmountString;
			}
		}
		
		//sum of all accounts including paying account. If sum exceeds 10 positions in String,
		//the 10 positions at the right are used.
		long sumOfAccountNumbers = 0;
		for(int i=0;i<posts.length;i++){
			sumOfAccountNumbers += Integer.parseInt(posts[i].accountNumber);
		}
		System.err.println(">>>>>>>>>>> sumOfAccountNumbers = " + sumOfAccountNumbers);
		
		sumOfAccountNumbers += ( posts.length * Integer.parseInt(accountNumber) );
		System.err.println(">>>>>>>>>>> sumOfAccountNumbers including paying account = " + sumOfAccountNumbers);
		
		String sumOfAccountNumberString = Long.toString(sumOfAccountNumbers);
		System.err.println(">>>>>>>>>>>>>>>> sumOfAccountNumberString = " + sumOfAccountNumberString);
		
		if(sumOfAccountNumberString.length() > 10){
			sumOfAccountNumberString.substring(sumOfAccountNumberString.length()-10);
		}
		
		if(sumOfAccountNumberString.length() != 10){
			int loopSize = 10-sumOfAccountNumberString.length();
			for(int i=0; i<loopSize; i++){
				sumOfAccountNumberString = "0"+sumOfAccountNumberString;
			}
		}
		
		int totalPosts = posts.length;
		String totalPostsString = Integer.toString(totalPosts);
		if(totalPostsString.length() != 7){
			int sizeOfLoop = 7-totalPostsString.length();
			for(int i = 1; i<=sizeOfLoop; i++){
				totalPostsString = "0"+totalPostsString;
			}
		}	
		
		batchCloseRecord = "9990A"+totalAmountString+sumOfAccountNumberString+totalPostsString;
		return batchCloseRecord;
	}
	public String getFileCloseRecord(){
		fileCloseRecord = "9999A";
		return fileCloseRecord;
	}
		public void kill() {
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClieOpMap();
            }
        });

	}
	
	public void setClieOPId(String clieOPId){
		this.clieOPId = clieOPId;
	}
	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public void setFixedDescription(String fixedDescription){
		this.fixedDescription = fixedDescription;
	}
	
	public void setProcessingDate(Date processingDate){
		if (processingDate == null){
			processingDateStr="000000";
		} else {
			SimpleDateFormat sd = new SimpleDateFormat("ddMMyy");
			try {
				processingDateStr = sd.format(processingDate);
			} catch (Exception e){
				System.err.println(e);
			}
		}
		this.processingDate = processingDate;
	}
}


