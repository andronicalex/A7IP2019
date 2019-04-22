package com.driverUserManagement.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.driverUserManagement.repositories.ProfilesRepository;
import com.driverUserManagement.repositories.UsersRepository;
import com.driverUserManagement.model.Profiles;
import com.driverUserManagement.model.RegisterDetails;
import com.driverUserManagement.model.Users;


@Service
public class RegisterService {

	private boolean isPasswordStrong(RegisterDetails user)
	{
        if(user.getPassword().length()<=20 && user.getPassword().length()>=4)
        {
        	 user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); 
             return true;
        }
        return false;
        
	}
	
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ProfilesRepository profilesRepository;
	

	public String addUser(RegisterDetails newUser) 
	{	
	
		if(  isPasswordStrong(newUser) && 
				!usersRepository.existsById(newUser.getUsername())
				&& !profilesRepository.existsById(newUser.getUsername()))
		{
			Users user = new Users(newUser.getUsername(),newUser.getPassword());
			Profiles userProfile = new Profiles(newUser.getUsername(),newUser.getEmail(),newUser.getPhone_number()
					,null,null);
				profilesRepository.save(userProfile);
				usersRepository.save(user);
				return "Succes";
		}
		return "Invalid data";
	}
}
