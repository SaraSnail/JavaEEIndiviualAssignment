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
        if (memberRepository.existsById(id)) {
            if(isMemberNotNullButWithAddressId(member)) {

                Optional<Address> findAddress = addressRepository.findById(member.getAddress().getId());
                if (findAddress.isPresent()) {
                    member.setId(id);
                    member.setAddress(findAddress.get());

                    memberRepository.save(member);

                    return new ResponseEntity<>("Member with id " + id + " updated successfully", HttpStatus.OK);

                } else {
                    throw new ResourceNotFoundException("Address", "id", member.getAddress().getId());
                }
            }else {

                member.setId(id);
                addressRepository.save(member.getAddress());
                memberRepository.save(member);

                return new ResponseEntity<>("Member with id " + id + " updated successfully", HttpStatus.OK);
            }
        }
        throw new ResourceNotFoundException("Member", "id", id);
    }


    @Override
    public Member addMember(Member member) {
        if(isMemberNotNullButWithAddressId(member)) {

            Optional<Address>findAddress = addressRepository.findById(member.getAddress().getId());
            if (findAddress.isPresent()) {
                member.setAddress(findAddress.get());
                return memberRepository.save(member);

            }else {
                throw new ResourceNotFoundException("Address", "id", member.getAddress().getId());
            }
        }else {
            addressRepository.save(member.getAddress());
            return memberRepository.save(member);
        }


    }

    @Override
    public ResponseEntity<String> deleteMember(Long id) {

        if(memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return new ResponseEntity<>("Member with id " + id + " deleted successfully", HttpStatus.OK);
        }

        throw new ResourceNotFoundException("Member", "id", id);
    }

    @Override
    public void deleteMemberById(Long id) {

        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);

        }else {
            throw new ResourceNotFoundException("Member", "id", id);
        }

    }


    private boolean isMemberNotNullButWithAddressId(Member member) {

        if (member.getAddress() == null) {
            throw new MissingParameterException("Address","Address object",member.getAddress());
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

        return isAddressNullButHaveAddressId(member);

    }

    private boolean isAddressNullButHaveAddressId(Member member){
        if(member.getAddress().getPostalCode() == null
                && member.getAddress().getCity() == null
                && member.getAddress().getStreet() == null)
        {

            if(member.getAddress().getId() != null){
                return true;
            }else {
                throw new MissingParameterException("Address.id","Long",member.getAddress().getId());
            }

        }else {

            if (member.getAddress().getCity() == null || member.getAddress().getCity().isEmpty()) {
                throw new MissingParameterException("city", "String", member.getAddress().getCity());
            }
            if (member.getAddress().getStreet() == null || member.getAddress().getStreet().isEmpty()) {
                throw new MissingParameterException("street", "String", member.getAddress().getStreet());
            }
            if (member.getAddress().getPostalCode() == null || member.getAddress().getPostalCode().isEmpty()) {
                throw new MissingParameterException("postalCode", "String", member.getAddress().getPostalCode());
            }
        }

        return false;
    }
}
