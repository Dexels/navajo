package com.dexels.navajo.adapter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.dexels.navajo.adapter.clieop.ClieOp2Post;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class ClieOp2Map implements Mappable {

	public ClieOp2Post[] posts;
	public ArrayList draftPosts = null;

	public String filePreRecord;
	public String batchPreRecord;
	public String fixedDescriptionRecord;
	public String constituentRecord;
	public String batchCloseRecord;
	public String fileCloseRecord;

	public String accountNumberSender;
	public String currency;
	public String fixedDescription;
	public Date processingDate;
	public String processingDateStr;
	
	//new
	public boolean isPayment; //CLIEOP used for making payments or receiving payments
	public String senderIdentification; //was 'KNVB2' -> now has to be filled in by user
	
	//name of constituent, must be 35 characters, spaces in example are mandatory
	public String constituentName; //was 'BU Betaald Voetbal                 ' now has to be filled in by user 
	

	public Binary content;
	FileOutputStream out;
	ByteArrayOutputStream bos;
	PrintStream p;

	public ClieOp2Map(){
		try {
			bos = new ByteArrayOutputStream();
			p = new PrintStream(bos);
		} catch (Exception e) {
			System.out.println("error in constructor: "+e);
		}
	}

	public void setPosts(ClieOp2Post[] posts) {
		this.posts = posts;
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public Binary getContent() throws UserException {
		Binary b = null;
		generateClieOP();
		b = new Binary(bos.toByteArray());
//		try {
//			out = new FileOutputStream("clieop.txt");
//			b.write(out);
//		} catch (Exception e) {
//			throw new UserException(-1, e.getMessage());
//		}
		return b;
	}

	private void generateClieOP(){
		p.print(getFilePreRecord()+"\r\n");
		p.print(getBatchPreRecord()+"\r\n");
		p.print(getFixedDescriptionRecord()+"\r\n");
		p.print(getConstituentRecord()+"\r\n");
		if (draftPosts != null) {
			posts = new ClieOp2Post[draftPosts.size()];
			posts = (ClieOp2Post[]) draftPosts.toArray();
		}
		for(int i=0; i<posts.length; i++){
			if(isPayment){
				p.print(posts[i].getTransactionRecord()+"\r\n");
				p.print(posts[i].getPaymentCharacteristicRecord()+"\r\n");
				p.print(posts[i].getDescriptionRecord()+"\r\n");
				p.print(posts[i].getBenaficiaryNameRecord()+"\r\n");
			} else {
				p.print(posts[i].getTransactionRecord()+"\r\n");
				p.print(posts[i].getPayerNameRecord()+"\r\n");
				p.print(posts[i].getPaymentCharacteristicRecord()+"\r\n");
				p.print(posts[i].getDescriptionRecord()+"\r\n");
			}
		}
		p.print(getBatchCloseRecord()+"\r\n");
		p.print(getFileCloseRecord());
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
			System.out.println("error in method getFilePrerecord: "+e);
		}
		filePreRecord = "0001A"+fileDateOfMaking+"CLIEOP03"+senderIdentification+fileDateOfMaking.substring(0,2)+"011";
		return filePreRecord;
	}
	public String getBatchPreRecord(){
		if(isPayment){
			batchPreRecord = "0010B"+"00"+accountNumberSender+"0001"+currency;
		} else {
			batchPreRecord = "0010B"+"10"+accountNumberSender+"0001"+currency;
		}
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
		constituentRecord = "0030B1"+processingDateStr+constituentName+"P";
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
		if(isPayment){
			for(int i=0;i<posts.length;i++){
				sumOfAccountNumbers += Integer.parseInt(posts[i].accountNumberReceiver);
			}
		} else {
			for(int i=0;i<posts.length;i++){
				sumOfAccountNumbers += Integer.parseInt(posts[i].accountNumberPayer);
			}
		}

		sumOfAccountNumbers = sumOfAccountNumbers + ( (long) posts.length * Long.parseLong(accountNumberSender) );

		String sumOfAccountNumberString = Long.toString(sumOfAccountNumbers);

		if(sumOfAccountNumberString.length() > 10){
			sumOfAccountNumberString = sumOfAccountNumberString.substring(sumOfAccountNumberString.length()-10);
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

          ClieOp2Map map = new ClieOp2Map();

          int size = 10;
          ClieOp2Post[] posts = new ClieOp2Post[size];
          map.setConstituentName("BU Betaald Voetbal                 ");
          map.setSenderIdentification("KNVB2");
          map.setProcessingDate(new Date());
          map.setFixedDescription("blaat");
          map.setIsPayment(false);
          map.setCurrency("EUR");
          map.setAccountNumberSender("0123456789");
          for(int i=0;i<size;i++){
            ClieOp2Post post = new ClieOp2Post();
            post.setAccountNumberReceiver("0123456789");
            post.setAmount("1234"+i);
            post.setAccountNumberPayer("0123456799");
            post.setAccountType("GIRO");
            post.setIsPayment(true);
            post.setLastName("de Clown");
            post.setPaymentDescription("hoelahoepen");
            posts[i] = post;
          }
          map.setPosts(posts);
          try {
        	  map.getContent();
		  } catch (UserException e) {
			  // TODO Auto-generated catch block
			  System.out.println("foutmelding 01 in main: "+e);
		  }
	}

	public void setAccountNumberSender(String accountNumberSender){
		if(accountNumberSender.length()!= 10){
			int sizeOfLoop = 10-accountNumberSender.length();
			for (int i=0;i<sizeOfLoop;i++){
				accountNumberSender = "0"+accountNumberSender;
			}
		}
		this.accountNumberSender = accountNumberSender;
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
				System.out.println("error in method setProcessingDate: "+e);
			}
		}
		this.processingDate = processingDate;
	}
	public void setSenderIdentification(String senderIdentification){
		this.senderIdentification = senderIdentification;
	}
	public void setIsPayment(boolean isPayment){
		this.isPayment = isPayment;
	}
	public void setConstituentName(String constituentName){
		this.constituentName = constituentName;
	}
}


