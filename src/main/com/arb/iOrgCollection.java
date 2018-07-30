package com.arb;

import java.util.List;

public interface iOrgCollection
{
   boolean INCLUSIVE = true;
   public iOrg getOrg(int orgId);
   public List<iOrg> getOrgTree(int orgId, boolean inclusive);
}
