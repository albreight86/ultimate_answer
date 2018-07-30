package com.arb; 
import java.util.Comparator;
import java.util.List;

public interface iOrg extends iOrgCollection, Comparator<iOrg> 
{
   //specified in requirements
	int getTotalNumUsers();
	int getTotalNumbFiles();
	int getTotalNumbBytes();
	List<iOrg> getChildOrgs();
	
	//needed to make comparator support simpler
   // ordering wasn't part of the requirements, but it makes testing and viewing results much easier.
	int getOrgId();
	int getParentOrgId();

	// need these as well
   List<User> getUsers();   // primarily for unit test.
   void incrementIndentLevel(int i);   // for determining the indent level
   int getIndentLevel();
   StringBuffer getStats();                // recursively generate stats output
}
