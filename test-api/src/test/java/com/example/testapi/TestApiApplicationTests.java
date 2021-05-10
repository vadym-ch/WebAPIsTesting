package com.example.testapi;
import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestApiApplicationTests {

    private static final String access_token = "j1JnvfPTfeYAAAAAAAAAAfhuCwb4AEoXojtr-kVN-JHIJV2zmtneBViR8uKpB6Ga";


    @Test
    public void a_uploadingTest() throws IOException, InterruptedException {
		
		File file = new File("hybrid.png");

        JSONObject json = new JSONObject();
        json.put("mode","add");
        json.put("autorename", true);
        json.put("path","/hybrid.png");


        RestAssured.given()
                .headers("Dropbox-API-Arg",json.toJSONString(),
                        "Content-Type","text/plain; charset=dropbox-cors-hack",
                        "Authorization", "Bearer " + access_token)
                .body(FileUtils.readFileToByteArray(file))
                .when().post("https://content.dropboxapi.com/2/files/upload")
                .then().statusCode(200);
    }

    @Test
    public void b_gettingMetadataTest(){


        JSONObject json = new JSONObject();
        json.put("path","/hybrid.png");

        RestAssured.given()
                .headers("Authorization", "Bearer " + access_token,
                        "Content-Type","application/json")
                .body(json.toJSONString())
                .when().post("https://api.dropboxapi.com/2/files/get_metadata")
                .then().statusCode(200);
    }

    @Test
    public void c_deletingTest(){

    
        JSONObject json = new JSONObject();
        json.put("path","/hybrid.png");

        RestAssured.given()
                .headers("Authorization", "Bearer " + access_token,
                        "Content-Type","application/json")
                .body(json.toJSONString())
                .when().post("https://api.dropboxapi.com/2/files/delete")
                .then().statusCode(200);
    }
}

