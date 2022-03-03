package com.personal.hashy;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class HashyApplication {

	public static void main(String[] args) {
		//simplest stuff: get the file and hash file for breaking this down.

		//need 3 args: digest, file-to-digest, expected...
		if (args.length < 3) {
			System.out.println("PLEASE SUPPLY SOME ARGS TO CHECK FILES");
			return;
		}

		String algorithmString = args[0];
		String fileToHash = args[1];
		String hashToCompare = args[2];

		try {
			isFileMatchingExpected(algorithmString, fileToHash, hashToCompare);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static void isFileMatchingExpected(String algorithmString, String fileToHash, String hashToCompare) throws NoSuchAlgorithmException {

		//get the message digest for digest string..
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(algorithmString);
		} catch (NoSuchAlgorithmException ex) {
			//no digest...
			System.out.print(ex.getMessage());
			throw ex;
		}

		File file = new File(fileToHash);
		File expectedHash = new File(hashToCompare);
		if (!file.exists() || !expectedHash.exists()) {
			System.out.println("Where the heck are the files?");
			return;
		}

		//hash file with the type, then compare to text of 2nd file
		FileInputStream readFile;

		try {
			//fd is opened on read; gather bytes to digest.
			readFile = new FileInputStream(file);

			int byteBuffLength = 524288; //512kB, 2^19
			byte[] fileByteBuff = new byte[byteBuffLength];

			//read while "have read" is less than filesize.
			long haveReadBytes = 0;
			long offset = file.length();
			//next chunk read is here to next buff length, unless end is near.
			while (haveReadBytes < offset) {

				int readByteLen = (int) (((offset - haveReadBytes) >= byteBuffLength) ? byteBuffLength : (offset - haveReadBytes));

				//read file and digest as we go along. (read into/from buffer starting at 0)
				int bytesRead = readFile.read(fileByteBuff, 0, readByteLen);
				digest.update(fileByteBuff, 0, bytesRead);

				haveReadBytes += bytesRead;
			}

			byte[] finishedDigest = digest.digest();

			readFile.close();

			final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
			//need to determine how to extract the digest...
			char[] hexDigest = new char[finishedDigest.length * 2];
			for (int j = 0; j < finishedDigest.length; j++) {
				int thisByte = finishedDigest[j] & 0xFF;
				hexDigest[j * 2] = HEX_ARRAY[thisByte >>> 4];
				hexDigest[j * 2 + 1] = HEX_ARRAY[thisByte & 0x0F];
			}
			String digestText = new String(hexDigest).toLowerCase(Locale.ROOT);

			// now need to read the other file to compare text to digest.
			BufferedReader hashReader = new BufferedReader(new FileReader(expectedHash));
			int ch;
			StringBuilder exHashStr = new StringBuilder();
			while ((ch = hashReader.read()) != -1) {
				exHashStr.append(Character.toString(ch));
			}
			hashReader.close();


			System.out.printf("COMPARING\n%s\n%s\n", digestText, exHashStr);
			//compare.
			if (digestText.equals(exHashStr.toString())) {
				System.out.printf("%s digest of %s MATCHES expected hash\n", algorithmString, fileToHash);
			} else {
				System.out.printf("%s digest of %s DOES NOT MATCH expected hash\n", algorithmString, fileToHash);
			}
		} catch (FileNotFoundException ex) {
			//missing file...
			System.out.print(ex.getMessage());
		} catch (Exception ex) {
			System.out.printf("monkaS: %s\n", ex.getMessage());
			ex.printStackTrace();
		}
	}

}
