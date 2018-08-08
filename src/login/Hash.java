package login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

	public static final String MD5 = "SHA-256";
	public static final String SHA_256 = "SHA-256";

	private String algoritmo ;


	public Hash(String algoritmo){
		this.algoritmo = algoritmo;
	}

	public String gerarHash(String senha){
		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance(this.algoritmo);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte messageDigest[] = null;
		try {
			messageDigest = algorithm.digest(senha.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		senha = hexString.toString();

		return senha;
	}

	public boolean ComparaHash(String hash1, String hash2){
		return hash1.equals(hash2);
	}

	public static  String gerarChaveEmail() {
		String[] carct ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 

		String senha=""; 


		for (int x=0; x<256; x++){ 
		int j = (int) (Math.random()*carct.length); 
		senha += carct[j]; 

		} 
		
		return senha;

	}


}
