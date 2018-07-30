package com.arb;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class OrgMainTest
{
   iOrgCollection orgs;

   @BeforeTest
   public void setup()
   {
      //write the org file
      try (BufferedWriter bw = Files.newBufferedWriter(FileSystems.getDefault().getPath("testOrgInputFile")))
      {
         bw.append("1, null, Foo\n");
         bw.append("2, 1, Bar\n");
         bw.append("4, 3, Baz\n");
         bw.append("5, 2, Qux\n");
         bw.append("3, null, BAC\n");
         bw.append("6, 3, BAC\n");
         bw.flush();
      } catch (IOException e)
      { }

      //write the users file
      try (BufferedWriter bw = Files.newBufferedWriter(FileSystems.getDefault().getPath("testUserInputFile")))
      {
         bw.append("100, 1, 101, 1001\n");
         bw.append("200, 2, 202, 2002\n");
         bw.append("4400, 3, 404, 4004\n");
         bw.append("555, 2, 505, 5005\n");
         bw.append("323, 4, 303, 3003\n");
         bw.append("676, 6, 606, 6006\n");
         bw.flush();
      } catch (IOException e)
      { }

      orgs = new OrgCollection("testOrgInputFile", "testUserInputFile");
   }

   @Test
   public void getOrg()
   {
      Assert.assertEquals(orgs.getOrg(1), new Org(1,0,"Foo"));
      Assert.assertEquals(orgs.getOrg(2), new Org(2,1,"Bar"));
      Assert.assertEquals(orgs.getOrg(3), new Org(3,0,"BAC"));
      Assert.assertEquals(orgs.getOrg(4), new Org(4,3,"Baz"));
      Assert.assertEquals(orgs.getOrg(5), new Org(5,2,"Qux"));
      Assert.assertEquals(orgs.getOrg(6), new Org(6,3,"BAC"));
   }

   @Test
   public void getOrgTree1()
   {
      List<iOrg> tree = orgs.getOrgTree(1, orgs.INCLUSIVE);
      
      Assert.assertEquals(tree.size(), 3);
      Assert.assertEquals(tree.get(0), new Org(1,0,"Foo"));
      Assert.assertEquals(tree.get(1), new Org(2,1,"Bar"));
      Assert.assertEquals(tree.get(2), new Org(5,2,"Qux"));
   }

   @Test
   public void getOrgTree6()
   {
      List<iOrg> tree = orgs.getOrgTree(6, orgs.INCLUSIVE);
      
      Assert.assertEquals(tree.size(), 1);
      Assert.assertEquals(tree.get(0), new Org(6,3,"BAC"));
   }

   @Test
   public void getOrgTree2NonInclusive()
   {
      List<iOrg> tree = orgs.getOrgTree(2, ! orgs.INCLUSIVE);
      
      Assert.assertEquals(tree.size(), 1);
      Assert.assertEquals(tree.get(0), new Org(5,2,"Qux"));
   }

   @Test
   public void getStats()
   {
      System.out.println(orgs.getOrg(0).getStats().toString());
   }

}
