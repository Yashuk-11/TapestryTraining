package org.apache.tapestry.Sampleproject.Acess;

import java.sql.SQLException;

import org.apache.tapestry.Sampleproject.model.Signupmodel;

public interface SignupAcces {
	public void save(Signupmodel user) throws SQLException;
}
