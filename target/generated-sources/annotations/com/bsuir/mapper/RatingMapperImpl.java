package com.bsuir.mapper;

import com.bsuir.dto.RatingResponse;
import com.bsuir.dto.UserDTO;
import com.bsuir.entity.Rating;
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
public class RatingMapperImpl implements RatingMapper {

    @Override
    public RatingResponse toDto(Rating rating) {
        if ( rating == null ) {
            return null;
        }

        RatingResponse.RatingResponseBuilder ratingResponse = RatingResponse.builder();

        ratingResponse.user( userToUserDTO( rating.getUser() ) );
        ratingResponse.id( rating.getId() );
        ratingResponse.comment( rating.getComment() );
        ratingResponse.rating( rating.getRating() );
        ratingResponse.createdAt( rating.getCreatedAt() );

        return ratingResponse.build();
    }

    @Override
    public Rating partialUpdate(RatingResponse ratingResponse, Rating rating) {
        if ( ratingResponse == null ) {
            return rating;
        }

        if ( ratingResponse.getId() != null ) {
            rating.setId( ratingResponse.getId() );
        }
        if ( ratingResponse.getComment() != null ) {
            rating.setComment( ratingResponse.getComment() );
        }
        if ( ratingResponse.getRating() != null ) {
            rating.setRating( ratingResponse.getRating() );
        }
        if ( ratingResponse.getUser() != null ) {
            if ( rating.getUser() == null ) {
                rating.setUser( User.builder().build() );
            }
            userDTOToUser( ratingResponse.getUser(), rating.getUser() );
        }
        if ( ratingResponse.getCreatedAt() != null ) {
            rating.setCreatedAt( ratingResponse.getCreatedAt() );
        }

        return rating;
    }

    @Override
    public List<RatingResponse> toListOfDto(List<Rating> ratingList) {
        if ( ratingList == null ) {
            return null;
        }

        List<RatingResponse> list = new ArrayList<RatingResponse>( ratingList.size() );
        for ( Rating rating : ratingList ) {
            list.add( toDto( rating ) );
        }

        return list;
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

    protected UserDTO userToUserDTO(User user) {
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

    protected void userDTOToUser(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        if ( userDTO.getId() != null ) {
            mappingTarget.setId( userDTO.getId() );
        }
        if ( userDTO.getUsername() != null ) {
            mappingTarget.setUsername( userDTO.getUsername() );
        }
        if ( userDTO.getIsActive() != null ) {
            mappingTarget.setIsActive( userDTO.getIsActive() );
        }
        if ( mappingTarget.getRoles() != null ) {
            List<Role> list = userDTO.getRoles();
            if ( list != null ) {
                mappingTarget.getRoles().clear();
                mappingTarget.getRoles().addAll( list );
            }
        }
        else {
            List<Role> list = userDTO.getRoles();
            if ( list != null ) {
                mappingTarget.setRoles( new ArrayList<Role>( list ) );
            }
        }
        if ( userDTO.getTotalPropertyCount() != null ) {
            mappingTarget.setTotalPropertyCount( userDTO.getTotalPropertyCount() );
        }
        if ( userDTO.getAllowPropertyCount() != null ) {
            mappingTarget.setAllowPropertyCount( userDTO.getAllowPropertyCount() );
        }
    }
}
