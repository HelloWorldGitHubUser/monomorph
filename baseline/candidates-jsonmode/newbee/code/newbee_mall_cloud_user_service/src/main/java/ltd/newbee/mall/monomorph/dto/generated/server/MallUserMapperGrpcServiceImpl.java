package ltd.newbee.mall.monomorph.dto.generated.server;

import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.dao.MallUserMapper;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.MallUserDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.MallUserMapperServiceGrpc;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.SelectByPrimaryKeyRequest;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.SelectByPrimaryKeyResponse;

/**
 * gRPC Service implementation for MallUserMapper.
 * - Handles gRPC requests for MallUserMapper API.
 * - Delegates to the injected MallUserMapper DAO and converts the returned
 *   entity to its protobuf DTO representation.
 */
public class MallUserMapperGrpcServiceImpl extends MallUserMapperServiceGrpc.MallUserMapperServiceImplBase {

    private final MallUserMapper mallUserMapperDao;

    public MallUserMapperGrpcServiceImpl(MallUserMapper mallUserMapperDao) {
        this.mallUserMapperDao = mallUserMapperDao;
    }

    /**
     * Implements the selectByPrimaryKey RPC.
     *
     * @param request          the request containing the userId lookup key
     * @param responseObserver the stream observer to send the response
     */
    @Override
    public void selectByPrimaryKey(SelectByPrimaryKeyRequest request,
                                   StreamObserver<SelectByPrimaryKeyResponse> responseObserver) {
        try {
            MallUser user = mallUserMapperDao.selectByPrimaryKey(request.getUserId());

            MallUserDTO mallUserDTO = (user == null)
                    ? null
                    : ltd.newbee.mall.monomorph.dto.generated.server.MallUserMapper.INSTANCE.toDTO(user);

            SelectByPrimaryKeyResponse.Builder responseBuilder = SelectByPrimaryKeyResponse.newBuilder();
            if (mallUserDTO != null) {
                responseBuilder.setMallUser(mallUserDTO);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}

