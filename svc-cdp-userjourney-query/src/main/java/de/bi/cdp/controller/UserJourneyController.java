package de.bi.cdp.controller;

import de.bi.cdp.model.UserJourneyResponse;
import de.bi.cdp.model.mapper.UserMapper;
import de.bi.cdp.model.mapper.UserResourceAssembler;
import de.bi.cdp.service.UserJourneyService;
import de.id.dataflow.audience.analytics.model.userjourneys.UserJourney;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserJourneyController {

    private final UserJourneyService userJourneyService;
    private final UserMapper userMapper;
    private final UserResourceAssembler userResourceAssembler;

    public UserJourneyController(UserJourneyService userJourneyService, UserMapper userMapper, UserResourceAssembler userResourceAssembler) {
        this.userJourneyService = userJourneyService;
        this.userMapper = userMapper;
        this.userResourceAssembler = userResourceAssembler;
    }

    /**
     * {@code GET  /the user journey data}.
     *
     * @return the ResponseEntity<String> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/user-journey-data")
    public ResponseEntity<String> getUserData(@RequestParam(name = "userId",
            defaultValue = "r_QlxgowOWpGF27HZuL400zA8mE0ZAxhpIEuXEawkz0KteU-nl9zHZv-OqxGGaBg") String userId
    ) {
        log.info("REST request to get name and address");
        String eventData = userJourneyService.getEventData(userId);
        return ResponseEntity.ok().body(eventData);
    }

    /**
     * Use of HATEOAS features, RepresentationModel
     *
     * {@code GET  /the user journey data}.
     *
     * @return the ResponseEntity<String> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/user-journey")
    public HttpEntity<?> getUserJourney() {
        log.info("REST request to get name and address");
        UserJourney userJourney = userJourneyService.getUserJourney();
        RepresentationModel<UserJourneyResponse> userResource =
                userResourceAssembler.toModel(userMapper.mapToUserResponse(userJourney));

        return ResponseEntity.ok(userResource);
    }

    /**
     * Use of HATEOAS features, RepresentationModel
     *
     * {@code GET  /the user journey data}.
     *
     * @return the HttpEntity<?> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/user-journey-all")
    public HttpEntity<?> getUsersJourneyAll() {
        log.info("REST request to get name and address");
        UserJourney userJourney = userJourneyService.getUserJourney();
        RepresentationModel<UserJourneyResponse> userResource =
                userResourceAssembler.toModel(userMapper.mapToUserResponse(userJourney));

        return ResponseEntity.ok(userResource);
    }

    /**
     * Use of HATEOAS features Link and EntityModel
     *
     * {@code GET  /the getUserJourneyLink}.
     *
     * @return the EntityModel<UserJourneyResponse> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/user-journey-link")
    public EntityModel<UserJourneyResponse> getUserJourneyLink() {
        log.info("REST request to get name and address");
        Class<UserJourneyController> controllerClass = UserJourneyController.class;

        // Start the affordance with the "self" link, i.e. this method.
        Link findOneLink = linkTo(methodOn(controllerClass).getUserJourneyLink()).withSelfRel();

        UserJourney userJourney = userJourneyService.getUserJourney();
        EntityModel<UserJourneyResponse> em = EntityModel.of(userMapper.mapToUserResponse(userJourney),
                findOneLink //
                        .andAffordance(afford(methodOn(controllerClass).getUserJourney())));
        return em;
    }

    /**
     * Use of HATEOAS features
     *
     * {@code GET  /the getUsersJourneyList}.
     *
     * @return the HttpEntity<?> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/user-journey-list")
    public HttpEntity<?> getUsersJourneyList() {
        log.info("REST request to get name and address");
        List<UserJourney> userJourneyRange = userJourneyService.getUserJourneyList();

        List<UserJourneyResponse> userJourneyResponses = userMapper.mapToListUserResponse(userJourneyRange);
        List<RepresentationModel<UserJourneyResponse>> userResource = userResourceAssembler.mapToLisModel(userJourneyResponses);

        return ResponseEntity.ok(userResource);
    }

    /**
     * {@code GET  /the rfv}.
     *
     * @return the ResponseEntity<String> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/rfv")
    public ResponseEntity<String> getRfvData() {
        log.info("REST request to get name and address");
        String eventData = userJourneyService.getRfvData();
        return ResponseEntity.ok().body(eventData);
    }

    /**
     * {@code GET  /the rfv}.
     *
     * @return the ResponseEntity<String> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/clientName")
    public ResponseEntity<String> getClientNameData() {
        log.info("REST request to get name and address");
        String eventData = userJourneyService.getClientNameData();
        return ResponseEntity.ok().body(eventData);
    }

    /**
     * {@code GET  /the sesionTimreStamp}.
     *
     * @return the ResponseEntity<String> with status {@code 200 (OK)} and the user data in body.
     */
    @GetMapping("/sessionTimeStamp")
    public ResponseEntity<String> getSessionTimeStamp() {
        log.info("REST request to get name and address");
        String eventData = userJourneyService.getSessionTimeStamp();
        return ResponseEntity.ok().body(eventData);
    }
}
