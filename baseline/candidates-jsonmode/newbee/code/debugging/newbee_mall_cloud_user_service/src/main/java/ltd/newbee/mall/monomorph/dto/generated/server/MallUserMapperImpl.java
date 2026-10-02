package ltd.newbee.mall.monomorph.dto.generated.server;

import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.dao.MallUserMapper;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.MallUserDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.MallUserMapperDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.MallUserMapperServiceGrpc;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.SelectByPrimaryKeyRequest;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.SelectByPrimaryKeyResponse;
import ltd.newbee.mall.monomorph.dto.generated.server.MallUserMapperMapper;

/**
 * gRPC Service implementation for MallUserMapper.
 * - Handles gRPC requests for MallUserMapper API.
 * - Interacts with Mapper for switching between DTO and MallUserMapper instances.
 */
public class MallUserMapperImpl extends MallUserMapperServiceGrpc.MallUserMapperServiceImplBase {

    /**
     * Implements the selectByPrimaryKey RPC.
     *
     * @param request          the request containing the DTO and userId
     * @param responseObserver the stream observer to send the response
     */
    @Override
    public void selectByPrimaryKey(SelectByPrimaryKeyRequest request,
                                   StreamObserver<SelectByPrimaryKeyResponse> responseObserver) {
        try {
            // 1. Map the incoming DTO to the original domain object (MallUserMapper)
            MallUserMapperDTO dto = request.getDto();
            MallUserMapper original = MallUserMapperMapper.INSTANCE.fromDTO(dto);

            // 2. Perform the business logic on the domain object
            MallUser user = original.selectByPrimaryKey(request.getUserId());

            // 3. Map the returned entity to its DTO using the entity mapper
            MallUserDTO mallUserDTO = ltd.newbee.mall.monomorph.dto.generated.server.MallUserMapper.INSTANCE.toDTO(user);

            // 4. Reuse the original DTO (domain object not modified) and build response
            SelectByPrimaryKeyResponse response = SelectByPrimaryKeyResponse.newBuilder()
                    .setDto(dto)
                    .setMallUser(mallUserDTO)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}