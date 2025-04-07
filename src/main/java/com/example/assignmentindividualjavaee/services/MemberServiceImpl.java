package com.example.assignmentindividualjavaee.services;

import com.example.assignmentindividualjavaee.entities.Address;
import com.example.assignmentindividualjavaee.entities.Member;
import com.example.assignmentindividualjavaee.exceptions.MissingParameterException;
import com.example.assignmentindividualjavaee.exceptions.ResourceNotFoundException;
import com.example.assignmentindividualjavaee.repositories.AddressRepository;
import com.example.assignmentindividualjavaee.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, AddressRepository addressRepository) {
        this.memberRepository = memberRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Member> getAllMembers() {
        if(memberRepository.findAll().isEmpty()) {
            throw new RuntimeException("Members repository is empty");
        }
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return member.get();
        }
        throw new ResourceNotFoundException("Member", "id", id);
    }

    @Override
    public ResponseEntity<String> updateMember(Member member, Long id) {
        Optional<Member>findMember = memberRepository.findById(id);
        if (findMember.isPresent()) {
            notNullMember(member);

            Optional<Address>findAddress = addressRepository.findById(member.getAddress().getId());
            if (findAddress.isPresent()) {
                member.setId(id);
                member.setAddress(findAddress.get());

                memberRepository.save(member);

                return new ResponseEntity<>("Member with id " + id + " updated successfully", HttpStatus.OK);

            }else {
                throw new ResourceNotFoundException("Address", "id", member.getAddress().getId());
            }

        }
        return new ResponseEntity<>("Couldn't find a member with id '"+ id +"'", HttpStatus.NOT_FOUND);
    }


    @Override
    public Member addMember(Member member) {
        notNullMember(member);
        Optional<Address>findAddress = addressRepository.findById(member.getAddress().getId());
        if (findAddress.isPresent()) {
            member.setAddress(findAddress.get());
            return memberRepository.save(member);
        }else {
            throw new ResourceNotFoundException("Address", "id", member.getAddress().getId());
        }

    }

    @Override
    public ResponseEntity<String> deleteMember(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);

        if (memberOptional.isPresent()) {
            memberRepository.deleteById(id);
            return new ResponseEntity<>("Member with id "+id+" deleted successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("Couldn't find a member with id '"+ id +"'", HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteMemberById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);

        if (memberOptional.isPresent()) {
            memberRepository.deleteById(id);

        }else {
            throw new ResourceNotFoundException("Member", "id", id);
        }

    }



    private void notNullMember(Member member) {
        System.out.println(member.toString());

        if (member.getAddress() == null) {
            throw new MissingParameterException("Address","class Address",member.getAddress());
        }


        if(member.getFirstName() == null || member.getFirstName().isEmpty()) {
            throw new MissingParameterException("firstName","String",member.getFirstName());
        }
        if(member.getLastName() == null || member.getLastName().isEmpty()) {
            throw new MissingParameterException("lastName","String",member.getLastName());
        }
        if (member.getEmail() == null || member.getEmail().isEmpty()) {
            throw new MissingParameterException("email","String",member.getEmail());
        }
        if (member.getDateOfBirth() == null){
            throw new MissingParameterException("dateOfBirth","LocalDate",member.getDateOfBirth());
        }


        if(member.getAddress().getId() == null){
            throw new MissingParameterException("Address.id","Long",member.getAddress().getId());

        }

    }
}
