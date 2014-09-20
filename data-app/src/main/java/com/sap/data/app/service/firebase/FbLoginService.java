package com.sap.data.app.service.firebase;

import org.json.JSONException;
import org.json.JSONObject;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.security.token.TokenGenerator;

public class FbLoginService {

	private static String token="0TCPPHmizFEz15eHJhpM3u7HOhEaRFbrm1iPhVu0";
	private static String FIREBASE_ROOT="https://dmapp-push.firebaseio.com/";
	static boolean flag=false;
	public String tokenGenerator(){
		
		JSONObject arbitraryPayload = new JSONObject();
		try {
		    arbitraryPayload.put("some", "arbitrary");
		    arbitraryPayload.put("data", "here");
		} catch (JSONException e) {
		    e.printStackTrace();
		}

		TokenGenerator tokenGenerator = new TokenGenerator(token);
		String token = tokenGenerator.createToken(arbitraryPayload);
		
		return token;
	}
	public boolean login(){
		
			Firebase dataRef = new Firebase(FIREBASE_ROOT);
			String token=tokenGenerator();
			dataRef.auth(token, new Firebase.AuthListener() {

		    @Override
		    public void onAuthError(FirebaseError error) {
		        System.err.println("Login Failed! " + error.getMessage());
		    }

		    @Override
		    public void onAuthSuccess(Object authData) {
		        System.out.println("Login Succeeded!");
		        flag=true;
		    }

		    @Override
		    public void onAuthRevoked(FirebaseError error) {
		        System.err.println("Authenticcation status was cancelled! " + error.getMessage());
		    }

		});
			return flag;
	}
}
