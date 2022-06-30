package com.ssafy.withssafy.service.team;

import com.ssafy.withssafy.dto.Team.TeamDto;
import com.ssafy.withssafy.entity.Team;
import com.ssafy.withssafy.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    private final ModelMapper modelMapper;


    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream().map(team -> modelMapper.map(team, TeamDto.class)).collect(Collectors.toList());
    }

    public TeamDto findLast() {
        List<Team> list = teamRepository.findAll(); 
        // 데이터가 없으면 null 반환
        if(list.size() == 0){
            return null;
        }
        
        return modelMapper.map(list.get(list.size()-1), TeamDto.class);
    }

    public TeamDto insert(TeamDto teamDto) {
        Team team = modelMapper.map(teamDto, Team.class);
        Team result = teamRepository.save(team);
        return modelMapper.map(result, TeamDto.class);
    }
}
