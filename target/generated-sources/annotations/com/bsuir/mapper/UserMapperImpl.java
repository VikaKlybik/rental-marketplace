package com.bsuir.mapper;

import com.bsuir.dto.UserDTO;
import com.bsuir.entity.Role;
import com.bsuir.entity.User;
import com.bsuir.entity.UserDetails;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T22:37:26+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName( userUserDetailsFirstName( user ) );
        userDTO.setLastName( userUserDetailsLastName( user ) );
        userDTO.setEmail( userUserDetailsEmail( user ) );
        userDTO.setPhone( userUserDetailsPhone( user ) );
        userDTO.setIconUrl( userUserDetailsIconUrl( user ) );
        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setIsActive( user.getIsActive() );
        userDTO.setTotalPropertyCount( user.getTotalPropertyCount() );
        userDTO.setAllowPropertyCount( user.getAllowPropertyCount() );
        List<Role> list = user.getRoles();
        if ( list != null ) {
            userDTO.setRoles( new ArrayList<Role>( list ) );
        }

        return userDTO;
    }

    @Override
    public List<UserDTO> toListOfDto(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( userList.size() );
        for ( User user : userList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.username( userDTO.getUsername() );
        user.isActive( userDTO.getIsActive() );
        List<Role> list = userDTO.getRoles();
        if ( list != null ) {
            user.roles( new ArrayList<Role>( list ) );
        }
        user.totalPropertyCount( userDTO.getTotalPropertyCount() );
        user.allowPropertyCount( userDTO.getAllowPropertyCount() );

        return user.build();
    }

    @Override
    public User partialUpdate(UserDTO userDTO, User user) {
        if ( userDTO == null ) {
            return user;
        }

        if ( userDTO.getId() != null ) {
            user.setId( userDTO.getId() );
        }
        if ( userDTO.getUsername() != null ) {
            user.setUsername( userDTO.getUsername() );
        }
        if ( userDTO.getIsActive() != null ) {
            user.setIsActive( userDTO.getIsActive() );
        }
        if ( user.getRoles() != null ) {
            List<Role> list = userDTO.getRoles();
            if ( list != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( list );
            }
        }
        else {
            List<Role> list = userDTO.getRoles();
            if ( list != null ) {
                user.setRoles( new ArrayList<Role>( list ) );
            }
        }
        if ( userDTO.getTotalPropertyCount() != null ) {
            user.setTotalPropertyCount( userDTO.getTotalPropertyCount() );
        }
        if ( userDTO.getAllowPropertyCount() != null ) {
            user.setAllowPropertyCount( userDTO.getAllowPropertyCount() );
        }

        return user;
    }

    private String userUserDetailsFirstName(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String firstName = userDetails.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String userUserDetailsLastName(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String lastName = userDetails.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String userUserDetailsEmail(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String email = userDetails.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String userUserDetailsPhone(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String phone = userDetails.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }

    private String userUserDetailsIconUrl(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String iconUrl = userDetails.getIconUrl();
        if ( iconUrl == null ) {
            return null;
        }
        return iconUrl;
    }
}
