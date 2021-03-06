package com.myproject.command;

import com.myproject.command.adminCommad.*;
import com.myproject.command.managerCommand.AcceptOrderCommand;
import com.myproject.command.managerCommand.DeclineOrderCommand;
import com.myproject.command.managerCommand.ViewOrdersCommand;
import com.myproject.command.userCommand.*;

/**
 * The PageAction represents class to store all actions related to the client request
 * it defines which command is required. Returns desired class which handle the needed request
 */
public enum PageAction {
    LOG_IN("login",new LoginCommand()),
    LOG_OUT("logout",new LogOutCommand()),
    REGISTER("register",new RegisterCommand()),
    FIND_ALL_USERS("findAllUsers",new FindAllUsersCommand()),
    FIND_ALL_CARS("findAllCars",new FindAllCarsCommand()),
    ADD_CAR("addCar",new AddCarCommand()),
    DELETE_CAR("deleteCar",new DeleteCar()),
    GET_INFO_ONE_CAR("getInfoCurrCar",new GetInfoOneCarCommand()),
    UPDATE_CAR_INFO("updateCar",new UpdateCarCommand()),
    TOP_UP_USER_BALANCE("topUpBalance",new TopUpBalanceCommand()),
    SORT_CARS_BY("wantedOrder",new SortCarsByWantedOrderCommand()),
    BLOCK_USER("block",new BlockUserCommand()),
    UNBLOCK_USER("unblock",new UnblockUserCommand()),
    SET_ROLE_MANAGER("setManager",new SetRoleForUserCommand()),
    UNSET_MANAGER_ROLE("unsetManager",new SetRoleForUserCommand()),
    BOOK_CAR_REQ("bookCarReq",new BookCarReqCommand()),
    CONFIRM_BOOKING("confirmBooking",new ConfirmBookingCommand()),
    COUNT_RECEIPT("countReceipt",new CountTotalReceiptCommand()),
    CANCEL_CAR_BOOKING("cancelBooking",new CancelCarBookingCommand()),
    ACCEPT_ORDER("acceptOrder",new AcceptOrderCommand()),
    DECLINE_ORDER_BY_MANAGER("declineOrder",new DeclineOrderCommand()),
    VIEW_USER_ORDERS("viewOrders",new ViewOrdersCommand()),
    VIEW_USER_ORDERS_PERSONAL("myOrders",new ViewMyOrdersCommand()),
    PAGINATION("pagination",new PaginationCommand()),
    SEARCH("search",new SearchCommand()),
    FORGOT_PASS("forgotPass",new ForgotPasswordCommand()),
    ACTIVATE_NEW_PASS("activateNewPassword",new ActivateNewPass()),
    UPDATE_DRIVER_PRICE("updateDriverPrice",new UpdateDriverPriceCommand());


    private final String action;
    private final Command command;

    PageAction(String action, Command command){
        this.action = action;
        this.command = command;
    }

    public String getAction() {

        return action;
    }

    public Command getCommand() {
        return command;
    }

    public static Command getCommand(String action){
        for (PageAction command: PageAction.values()) {
            if (command.getAction().equals(action)){
                return command.command;
            }
        }
        return null;
    }
    }