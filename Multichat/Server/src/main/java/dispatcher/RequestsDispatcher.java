package dispatcher;

import context.Component;
import protocol.Request;
import protocol.Response;
import server.ClientHandler;
import service.*;

public class RequestsDispatcher implements Component {
    private LoginService loginService;
    private MessageService messageService;
    private OrderService orderService;
    private PaginationService paginationService;
    private ProductService productService;
    private ClientHandler clientHandler;

    public RequestsDispatcher() {

    }

    public void handleRequest(String jsonRequest) {
        Request request = new Request(jsonRequest);
        String command = request.getCommand();
        switch (command) {
            case "Login": {
                clientHandler.sendMessage(Response.build(loginService.login(request)));
            }
            break;
            case "Message": {
                clientHandler.sendMessageAllClient(Response.build(messageService.sendMessage(request)));
            }
            break;
            case "get messages": {
                clientHandler.sendMessage(Response.build(paginationService.getMessages(request)));
            }
            break;
            case "set product": {
                productService.addProduct(request);
            }
            break;
            case "get products": {
                clientHandler.sendMessage(Response.build(productService.getProducts()));
            }
            break;
            case "delete product": {
                productService.deleteProduct(request);
            }
            break;
            case "buy product": {
                orderService.addProduct(request);
            }
            break;
            case "get orders": {
                clientHandler.sendMessage(Response.build(orderService.getOrders(request)));
            }
        }

    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public PaginationService getPaginationService() {
        return paginationService;
    }

    public void setPaginationService(PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public String getComponentName() {
        return "requestsDispatcher";
    }
}

