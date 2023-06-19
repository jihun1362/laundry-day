package com.meta.laundry_day.order.controller;

import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ResultCode;
import com.meta.laundry_day.order.dto.LaundryRequestDto;
import com.meta.laundry_day.order.dto.OrderReaponseDto;
import com.meta.laundry_day.order.dto.OrderRequestDto;
import com.meta.laundry_day.order.dto.ProgressResponeDto;
import com.meta.laundry_day.order.service.OrderService;
import com.meta.laundry_day.security.util.UserDetailsImpl;
import com.meta.laundry_day.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.meta.laundry_day.common.message.ResultCode.LAUNDRY_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.LAUNDRY_REGIST_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.LAUNDRY_UPDATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ORDER_CREATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ORDER_DELETE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ORDER_PROGRESS_CHECK_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ORDER_PROGRESS_UPDATE_SUCCESS;
import static com.meta.laundry_day.common.message.ResultCode.ORDER_REQUEST_SUCCESS;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{cardId}")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<ResultCode>> createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                               @RequestBody OrderRequestDto requestDto,
                                                               @PathVariable Long cardId) {
        orderService.createOrder(userDetails.getUser(), requestDto, cardId);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(ORDER_CREATE_SUCCESS, null));
    }

    @GetMapping("/details/{orderId}")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<OrderReaponseDto>> orderDetail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                     @PathVariable Long orderId) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ORDER_REQUEST_SUCCESS, orderService.orderDetail(userDetails.getUser(), orderId)));
    }

    @PostMapping(value = "/laundry/{stablepriceId}/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> createLaundry(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                           @RequestPart LaundryRequestDto requestDto,
                                                                           @RequestPart MultipartFile image,
                                                                           @PathVariable Long stablepriceId,
                                                                           @PathVariable Long orderId) throws IOException {
        orderService.createLaundry(userDetails.getUser(), requestDto, image, stablepriceId, orderId);
        return ResponseEntity.status(201)
                .body(new ResponseDto<>(LAUNDRY_CREATE_SUCCESS, null));
    }

    @PatchMapping("/laundry/done/{progressId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> doneLaundry(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable Long progressId) {
        orderService.doneLaundry(userDetails.getUser(), progressId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(LAUNDRY_REGIST_SUCCESS, null));
    }

    @PatchMapping("/laundry/{laundryId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> updateLaundry(@RequestParam String status,
                                                                 @PathVariable Long laundryId) {
        orderService.updateLaundry(status, laundryId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(LAUNDRY_UPDATE_SUCCESS, null));
    }

    @GetMapping("/progress")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ProgressResponeDto>> progressCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ORDER_PROGRESS_CHECK_SUCCESS, orderService.progressCheck(userDetails.getUser())));
    }

    @PatchMapping("/progress/{progressId}")
    @Secured(UserRoleEnum.Authority.ADMIN)
    public ResponseEntity<ResponseDto<ResultCode>> updateProgress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @RequestParam String status,
                                                                 @PathVariable Long progressId) {
        orderService.updateProgress(userDetails.getUser(), status, progressId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ORDER_PROGRESS_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/{orderId}")
    @Secured(UserRoleEnum.Authority.USER)
    public ResponseEntity<ResponseDto<ResultCode>> deleteOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @PathVariable Long orderId) {
        orderService.deleteOrder(userDetails.getUser(), orderId);
        return ResponseEntity.status(200)
                .body(new ResponseDto<>(ORDER_DELETE_SUCCESS, null));
    }
}
