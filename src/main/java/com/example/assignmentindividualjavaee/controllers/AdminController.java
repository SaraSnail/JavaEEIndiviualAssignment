package com.example.assignmentindividualjavaee.controllers;

import com.example.assignmentindividualjavaee.entities.Member;
import com.example.assignmentindividualjavaee.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    @Autowired
    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/updatemember/{id}")
    public ResponseEntity<String> updateMember(@RequestBody Member member, @PathVariable("id") Long id) {
        return memberService.updateMember(member, id);
    }

    @PostMapping("/addmember")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        return new ResponseEntity<>(memberService.addMember(member), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletemember/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") Long id) {
        return memberService.deleteMember(id);
    }

    @PostMapping("/deletememberbyid")
    public String deleteMemberById(@RequestParam("id") Long id) {
        memberService.deleteMemberById(id);
        return "redirect:/admin/deletemember";
    }

    @GetMapping("/deletemember")
    public String deleteMemberWeb(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "membersweb";
    }

}
