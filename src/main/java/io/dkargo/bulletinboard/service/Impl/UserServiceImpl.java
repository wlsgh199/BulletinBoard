package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqUserDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * 회원 추가
     *
     * @param reqUserDTO 회원 정보
     */
    @Override
    public void saveMember(ReqUserDTO reqUserDTO) {
        User user = new User(reqUserDTO);
//        user.setCreateTime(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public User findMemberById(Long id) {   //TODO: 이름 변경
        return userRepository
                .findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
