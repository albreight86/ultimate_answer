package com.arb;

public class OrgMain
{
   public static String ORGS_FILE = "testOrgInputFile";
   public static String USERS_FILE = "testUserInputFile";

   public static void main(String args[])
   {
       iOrgCollection orgs = new OrgCollection(ORGS_FILE, USERS_FILE);
       
       System.out.println(orgs.getOrg(0).getStats().toString());

   }
}