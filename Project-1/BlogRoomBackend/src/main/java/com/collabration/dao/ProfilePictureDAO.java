package com.collabration.dao;

import com.collabration.model.ProfilePicture;

public interface ProfilePictureDAO {

	public void uploadProfilePicture(ProfilePicture profilePicture);
	public ProfilePicture getProfilePicture(String email);
}
