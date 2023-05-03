package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * 회원 추가
     *
     * @param reqAddUserDTO 회원 정보
     */
    @Override
    public void saveMember(ReqAddUserDTO reqAddUserDTO) {
        User user = User.builder()
                .userName(reqAddUserDTO.getUserName())
                .build();

        userRepository.save(user);
    }
}
