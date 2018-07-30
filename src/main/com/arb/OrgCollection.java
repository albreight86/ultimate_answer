package com.arb;

import java.util.List;
import java.util.Map;

public class OrgCollection implements iOrgCollection 
{
   private Map<Integer, Org>orgs;

   private List<User> users;

   public OrgCollection(String orgsFile, String usersFile)
   {
      orgs = Utils.processOrgFile(orgsFile);

      users = Utils.processUserFile(usersFile);
      
      orgs = Utils.addUsersToOrgs(orgs, users);
   }

   @Override
   final public iOrg getOrg(int orgId)
   {
     return orgs.get(orgId);
   }

   @Override
   final public List<iOrg> getOrgTree(int orgId, boolean inclusive)
   {
      List<iOrg> tree = orgs.get(orgId).getOrgTree(orgId, inclusive);

     return tree;
   }
}
