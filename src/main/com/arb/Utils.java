package com.arb;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils
{
   public static int ZEROETH_PARENT_ORG_ID = -1;
   public static String ZEROETH_PARENT_ORG_NAME = "ZeroethParent";
   private static int ORG_ID_AT_TOP_OF_TREE = 0;
   private static int INVALID_INT = -1;
   public static String COMMA_SPACE = ", ";

   public static Map<Integer, Org>  addUsersToOrgs(Map<Integer, Org> orgs, List<User> users)
   {
      users.forEach(u -> orgs.get(u.getOrgId()).addUser(u));
      return orgs;
   }

   public static List<User> processUserFile(String inputUserFile)
   {
      List<User> userList = new ArrayList<User>();
      // reads inputFile from JVM execution directory
      try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(inputUserFile)))
      {
         //userList = br.lines().map(consumeUsers).collect(Collectors.toList());
         userList = br.lines().map(consumeUsers).collect(Collectors.toList());
         br.close();
      } catch (IOException e)
      { }

      return userList;
   }

   private static Function<String, User > consumeUsers = (line) -> {
      String[] fields = line.split(COMMA_SPACE);
      if (fields.length != 4)
      {
         System.out.println("ERROR: invalid user line: " + line);
         return User.USER_NULL;
      }
   
      int userId = convertStringToInt(fields[0]);
      if (userId == INVALID_INT)
      {
         System.out.println("ERROR: invalid user, line: " + line);
         return User.USER_NULL;
      }
   
      int orgId = convertStringToInt(fields[1]);
      if (orgId == INVALID_INT)
      {
         System.out.println("ERROR: invalid user, line: " + line);
         return User.USER_NULL;
      }
   
      int numFiles = convertStringToInt(fields[2]);
      if (numFiles == INVALID_INT)
      {
         System.out.println("ERROR: invalid user, line: " + line);
         return User.USER_NULL;
      }
   
      int numBytes = convertStringToInt(fields[3]);
      if (numBytes == INVALID_INT)
      {
         System.out.println("ERROR: invalid user, line: " + line);
         return User.USER_NULL;
      }
      
      return new User(userId, orgId, numFiles, numBytes);
   };

   public static Map<Integer, Org> processOrgFile(String inputFile)
   {
      Map<Integer, Org> orgMap = new TreeMap<>();
      // reads inputFile from JVM execution directory
      try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(inputFile)))
      {
         orgMap = br.lines()
                        .map(mapToOrg)
                        .collect(Collectors.toMap(o -> o.getOrgId(), o -> o));
      } catch (IOException e)
      { 
         int i = 9;
         i = 2;
      }


      //need the ultimate parent whose org id is 0 and whose children are
      // all the parentless orgs.. Make it's parent -1; it's value doesnt matter.
      orgMap.get(0);
      orgMap.put(0, new Org(0, ZEROETH_PARENT_ORG_ID, ZEROETH_PARENT_ORG_NAME));
      
      //go through the entire list of orgs to add the children to their parents.
      Set<Integer> keys = orgMap.keySet();
      Collection<Org> values = orgMap.values();
      for ( Org org : values)
         {
                int parentOrgId = (org.getParentOrgId());
                if(parentOrgId == ZEROETH_PARENT_ORG_ID)
                    continue;

                Org parentOrg = orgMap.get(parentOrgId);
                parentOrg.addChildOrg(org);
         }
      return orgMap;
   }

   // takes each line in the org file and return as an iOrg object.
   // Returns ORG_NULL if the line is mis-formatted.
   private static Function<String, Org> mapToOrg = (line) -> {
      String[] fields = line.split(COMMA_SPACE);
      if (fields.length != 3)
      {
         System.out.println("ERROR: invalid line: " + line);
         return Org.ORG_NULL;
      }

      int orgId = convertStringToInt(fields[0]);
      if (orgId == INVALID_INT)
      {
         System.out.println("ERROR: invalid orgId, line: " + line);
         return Org.ORG_NULL;
      }

      int parentId = ORG_ID_AT_TOP_OF_TREE;
      if ( ! fields[1].equals("null"))
      {
         parentId = convertStringToInt(fields[1]);
         if (parentId == INVALID_INT)
         {
            System.out.println("ERROR: invalid parentId, line: " + line);
            return Org.ORG_NULL;
         }
      }

      return new Org(orgId, parentId, fields[2]);
   };

   private static int convertStringToInt(String str)
   {
      int number = INVALID_INT;
      try
      {
         number = Integer.valueOf(str);
      } catch (NumberFormatException ex)
      {
         System.out.println("ERROR: invalid integer: " + str);
      }
      return number;
   }

}
