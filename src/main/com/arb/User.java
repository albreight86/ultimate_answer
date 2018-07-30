package com.arb;

import java.util.Comparator;

public class User implements Comparator<User>
{
	private final int userId;
	private final int orgId;
	private final int numFiles;
	private final int numBytes;
	
	public static User USER_NULL = new User(0, 0, 0, 0);

	public User(int userId, int orgId, int numFiles, int numBytes) {
		this.userId = userId;
		this.orgId = orgId;
		this.numFiles = numFiles;
		this.numBytes = numBytes;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", orgId=" + orgId + ", numFiles=" + numFiles + ", numBytes=" + numBytes
				+ "]";
	}

	public int getUserId() { return userId; }
	public int getOrgId() { return orgId; }
	public int getNumFiles() { return numFiles; }
	public int getNumBytes() { return numBytes; }

	@Override
   public int compare(User u1, User u2)
   {
	      if (u1.getUserId() > u2.getUserId())
	         return 1;
	      if (u1.getUserId() == u2.getUserId())
	         return 0;

	      return -1;
   }

   @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numBytes;
		result = prime * result + numFiles;
		result = prime * result + orgId;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (numBytes != other.numBytes)
			return false;
		if (numFiles != other.numFiles)
			return false;
		if (orgId != other.orgId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	

}
