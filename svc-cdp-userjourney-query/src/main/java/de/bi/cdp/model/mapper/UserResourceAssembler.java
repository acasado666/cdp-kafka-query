package de.bi.cdp.model.mapper;

import de.bi.cdp.controller.UserJourneyController;
import de.bi.cdp.model.UserJourneyResponse;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Antonio Casado
 * @since 14/07/2021
 */
@Component
public class UserResourceAssembler implements RepresentationModelAssembler<UserJourneyResponse, RepresentationModel<UserJourneyResponse>> {


    @Override
    public RepresentationModel<UserJourneyResponse> toModel(UserJourneyResponse entity) {
        entity.add(linkTo(methodOn(UserJourneyController.class).getUserJourney()).withSelfRel());
        return entity;
    }

    public List<RepresentationModel<UserJourneyResponse>> mapToLisModel(List<UserJourneyResponse> l) {
        return (l).stream().map(this::toModel).collect(Collectors.toList());
    }
}
