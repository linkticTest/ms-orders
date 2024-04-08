package com.spring.aws.config;

import com.spring.aws.dto.*;
import com.spring.aws.model.*;
import com.spring.aws.service.OrderService;
import com.spring.aws.service.ProductService;
import com.spring.aws.service.UserService;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

import java.util.function.Function;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AwsLambdaConfig {

    private static Logger logger = LoggerFactory.getLogger(AwsLambdaConfig.class);
    private final long loggerID = new Random().nextLong();

    @Bean
    public Filter getFilter(){
        return new SecurityFilter();
    }

    @Autowired
    private RedisTemplate<String, Object> template = new RedisTemplate<>();

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;


    @Bean
    public Function<AwsLambdaConfigRequest, AwsLambdaConfigResponse> processCharacters()  {

        AwsLambdaConfigResponse awsLambdaConfigResponse = new AwsLambdaConfigResponse();
        awsLambdaConfigResponse.setIdOrder("");
        return (param) -> {
            try {
                AwsLambdaConfigRequest awsLambdaConfigRequest = (AwsLambdaConfigRequest) param;
                logger.info("{}:Request={}", loggerID, new Gson().toJson(awsLambdaConfigRequest));
                Order order = new Order();
                String authToken = awsLambdaConfigRequest.getToken();
                logger.info("{}:authToken={}", loggerID, authToken);
                UserDTO user = (UserDTO) template.opsForValue().get(authToken);
                logger.info("{}:usuario={}", loggerID, user);
                if (user != null) {
                    order = createOrder(user, awsLambdaConfigRequest);
                    orderService.saveOrder(order);
                    awsLambdaConfigResponse.setCode(200);
                    awsLambdaConfigResponse.setMessage("Orden generada con exito");
                    awsLambdaConfigResponse.setIdOrder(order.getIdOrder());
                    return awsLambdaConfigResponse;
                } else {
                    awsLambdaConfigResponse.setCode(400);
                    awsLambdaConfigResponse.setMessage("sesion invalida");
                    return awsLambdaConfigResponse;
                }
            }catch (Exception e){
                awsLambdaConfigResponse.setCode(400);
                awsLambdaConfigResponse.setMessage(e.getMessage());
                return awsLambdaConfigResponse;

            }
        };
    }

    @Bean
    public Function<UserDTO, OrderResponse> getOrder(){

        return (param) -> {
            logger.info("{}:param={}", loggerID, param);
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderDTO(new ArrayList<>());
            /*OrderDTO orderDTO = new OrderDTO();*/
            Response response = new  Response();
            //orderDTO.setIdOrder(param);
            try{
                if(param != null){
                    List<Order> orders = orderService.getOrders(param.getIdNumber());
                    if(orders != null && orders.size() > 0) {
                        for(Order order : orders){
                            OrderDTO orderDTO = new OrderDTO();
                            orderDTO.setIdOrder(order.getIdOrder());
                            orderDTO.setCreateDt(order.getCreateDt());
                            orderDTO.setUpdateDt(order.getUpdateDt());
                            orderDTO.setStatus(order.getStatus());
                            Optional<User> user = userService.getUSerById(order.getIdUser());
                            final UserDTO userDTO = getUserDTO(user);
                            orderDTO.setUserDTO(userDTO);
                            List<String> result = Arrays.stream(order.getProducts().split(",")).toList();
                            List<Integer> intList = new ArrayList<>();
                            for (String s : result){
                                String str = s.replaceAll("\\D+","");
                                intList.add(Integer.valueOf(str));
                            }
                            List<Product> products = productService.getProductsById(intList);
                            orderDTO.setProducts(products);
                            orderResponse.getOrderDTO().add(orderDTO);
                        }
                        response.setCode(200);
                        response.setMessage("Transaccion exiotsa");
                        orderResponse.setResponse(response);
                    }else{
                        response.setCode(400);
                        response.setMessage("Orden no existe");
                        orderResponse.setResponse(response);
                        orderResponse.setOrderDTO(null);
                    }
                }else{
                    response.setCode(400);
                    response.setMessage("Parametro no valido");
                    orderResponse.setResponse(response);
                    orderResponse.setOrderDTO(null);
                }
                return orderResponse;
            }catch (Exception e){
                response.setCode(400);
                response.setMessage(e.getMessage());
                orderResponse.setResponse(response);
                orderResponse.setOrderDTO(null);
                return orderResponse;
            }
        };
    }

    private static UserDTO getUserDTO(Optional<User> user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.get().getFirstName());
        userDTO.setLastName(user.get().getLastName());
        userDTO.setIdType(user.get().getIdType());
        userDTO.setIdNumber(user.get().getIdNumber());
        userDTO.setAddress(user.get().getAddress());
        userDTO.setPhone(user.get().getPhone());
        userDTO.setCityOfResidence(user.get().getCityOfResidence());
        return userDTO;
    }

    public Order createOrder(UserDTO user, AwsLambdaConfigRequest awsLambdaConfigRequest) {
        logger.info("{}:Ingresa al metodo createOrder. Class:{}",loggerID,this.getClass().getName());

        Order order = new Order();
        User userOrder = new User();
        String idOrder = java.util.UUID.randomUUID().toString();
        idOrder.replaceAll("-", "");
        idOrder.substring(0, 10);

        userOrder.setFirstName(user.getFirstName());
        userOrder.setLastName(user.getLastName());
        userOrder.setIdType(user.getIdType());
        userOrder.setIdNumber(user.getIdNumber());
        userOrder.setAddress(user.getAddress());
        userOrder.setPhone(user.getPhone());
        userOrder.setCityOfResidence(user.getCityOfResidence());

        order.setIdOrder(idOrder);
        order.setIdUser(user.getIdNumber());
        order.setCreateDt(new Date());
        order.setUpdateDt(new Date());
        order.setStatus("Activo");
        List<Product> products = productService.getProductsById(awsLambdaConfigRequest.getProducts());
        order.setProducts(awsLambdaConfigRequest.getProducts().toString());
        logger.info("{}:products={}",loggerID, new Gson().toJson(products));
        logger.info("{}:order={}",loggerID, new Gson().toJson(order));
        return order;
    }

}
