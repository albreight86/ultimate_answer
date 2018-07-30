package com.arb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Org implements iOrg
{

   private final int orgId;
   private final int parentOrgId;
   private final String orgName;

   private int indentLevel;

   public static Org ORG_NULL = new Org(0, 0, "NULL_ORG");
   public static String INDENT="  ";

   public static  Comparator<iOrg> compareByOrgId = (iOrg o1, iOrg o2) -> o1.compare(o1, o2);
   private Set<Org> childOrgs = new TreeSet<Org>(compareByOrgId);
   private Comparator<User> compareByUserId = (User u1, User u2) -> u1.compare(u1, u2);
   private Set<User> users = new TreeSet<User>(compareByUserId);

   public Org(int orgId, int parentOrgId, String orgName)
   {
      this.orgId = orgId;
      this.parentOrgId = parentOrgId;
      this.orgName = orgName;
   }

   // the added child is placed in the child Set ordered by OrgId.
   final public void addChildOrg(Org child)
   {
      childOrgs.add(child);
   }

   // the added user is placed in the user Set ordered by UserId.
   final public void addUser(User user)
   {
      users.add(user);
   }


   @Override
   final public int getIndentLevel()
   {
      return indentLevel;
   }

   @Override
   final public void incrementIndentLevel(int level)
   {
      indentLevel = level + 1;
   }

   // Ignoring the ordId......not sure it is useful for other than calling the top of the tree.
   @Override
   final public List<iOrg> getOrgTree(int orgId, boolean inclusive)
   {
      List<iOrg> children = (List<iOrg>) childOrgs
                                      .stream()
                                      .map(c ->  c.getOrgTree(0, true))
                                      .flatMap(list ->  list.stream())
                                      .collect(Collectors.toCollection(ArrayList::new));
      if(inclusive)
      {
         children.add(0, this);
      }
      return children;
   }

   @Override
   final public int getTotalNumUsers()
   {
      int childTotal = childOrgs.stream().map(c -> c.getTotalNumUsers()).reduce(0, (a, b) -> a + b);
      return childTotal + users.size();
   }

   @Override
   final public int getTotalNumbFiles()
   {
      int childTotal =  childOrgs.stream().map(c -> c.getTotalNumbFiles()).reduce(0, (a, b) -> a + b);
      return childTotal + users.stream().map(c -> c.getNumFiles()).reduce(0, (a, b) -> a + b);
   }

   @Override
   final public int getTotalNumbBytes()
   {
      int childTotal = childOrgs.stream().map(c -> c.getTotalNumbBytes()).reduce(0, (a, b) -> a + b);
      return users.stream().map(c -> c.getNumBytes()).reduce(0, (a, b) -> a + b);
   }

   @Override
   final public List<iOrg> getChildOrgs()
   {
      return childOrgs.stream().collect(Collectors.toCollection(ArrayList::new));
   }

   @Override
   final public List<User> getUsers()
   {
      return users.stream().collect(Collectors.toCollection(ArrayList::new));
   }

   @Override
   // order by orgId, then parent orgId
   final public int compare(iOrg o1, iOrg o2)
   {
      int comp = Integer.compare(o1.getOrgId(), o2.getOrgId());
      
      if( comp != 0)
      {
         return comp;
      }
      return Integer.compare(o1.getParentOrgId(), o2.getParentOrgId());
   }
   
   //the org that getStats is called from determines the indent level
   @Override
   final public StringBuffer getStats()
   {
      childOrgs.forEach( c -> c.incrementIndentLevel(indentLevel));
      StringBuffer buf = getThisStats();
      StringBuffer childStats = childOrgs.stream()
                                          .map(c -> c.getStats())
                                          .reduce(new StringBuffer(), (a, b) -> a.append(b));
      buf.append(childStats);
      return buf;
   }
   
   final public StringBuffer getThisStats()
   {
      StringBuffer buf = new StringBuffer();
      for (int i=0; i<indentLevel; i++)
      {
         buf.append(INDENT);
      }
      
      buf.append( orgId);
      buf.append(": total users: " + getTotalNumUsers());
      buf.append(", total files: " + getTotalNumbFiles());
      buf.append(", total bytes: " + getTotalNumbBytes());
      buf.append("\n");

      return buf;
   }
   

   @Override
   final public String toString()
   {
      return "Org[parentOrgId=" + parentOrgId + ", orgName=" + orgName + ", orgId=" + orgId + "]";
   }

   final public int getOrgId()
   {
      return orgId;
   }

   final public int getParentOrgId()
   {
      return parentOrgId;
   }

   final public String getOrgName()
   {
      return orgName;
   }

   @Override
   final public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + orgId;
      result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
      result = prime * result + parentOrgId;
      return result;
   }

   @Override
   final public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Org other = (Org) obj;
      if (orgId != other.orgId)
         return false;
      if (orgName == null) {
         if (other.orgName != null)
            return false;
      } else if (!orgName.equals(other.orgName))
         return false;
      if (parentOrgId != other.parentOrgId)
         return false;
      return true;
   }

   // this is not needed, would like to get rid of it 
   @Override
   final public iOrg getOrg(int orgId)
   {
      return null;
   }
}
