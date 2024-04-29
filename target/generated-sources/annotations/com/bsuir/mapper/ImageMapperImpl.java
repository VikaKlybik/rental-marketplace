package com.bsuir.mapper;

import com.bsuir.dto.ImageResponse;
import com.bsuir.entity.Image;
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
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageResponse toDto(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponse.ImageResponseBuilder imageResponse = ImageResponse.builder();

        imageResponse.id( image.getId() );
        imageResponse.url( image.getUrl() );

        return imageResponse.build();
    }

    @Override
    public Image partialUpdate(ImageResponse imageResponse, Image image) {
        if ( imageResponse == null ) {
            return image;
        }

        if ( imageResponse.getId() != null ) {
            image.setId( imageResponse.getId() );
        }
        if ( imageResponse.getUrl() != null ) {
            image.setUrl( imageResponse.getUrl() );
        }

        return image;
    }

    @Override
    public List<ImageResponse> toListOfDto(List<Image> images) {
        if ( images == null ) {
            return null;
        }

        List<ImageResponse> list = new ArrayList<ImageResponse>( images.size() );
        for ( Image image : images ) {
            list.add( toDto( image ) );
        }

        return list;
    }
}
