# TRNG-2499 Group 8 Team Working Agreement

**Revision:** 262511
**Effective Date:** 2026-11-25
**Project:** P1 Banking App
**Team Members:** Alex Schectman, Santosh Subedi

---

# 1. Purpose

This agreement establishes expectations and procedures for communication, decision-making, code review, task ownership, and project execution.

The objective of this agreement is to ensure steady project progress, reduce avoidable delays, and provide a clear process for resolving blockers and making decisions when time-sensitive action is required.

__All team members acknowledge that successful, timely project completion is the team's primary objective.__

---

# 2. Communication Standards

## 2.1 Primary Communication Channels

Project communication shall occur through one or more of the following:

- Discord (primary)
- GitHub Issues (project management, planning, and decisions)
- GitHub Pull Requests (code review)
- Team meetings or screen-sharing sessions (co-working and pair debugging)

Project-related decisions must be documented in writing through one of the above channels.

---

## 2.2 Response Expectations

Because the project operates under a condensed timeline, team members agree to make reasonable efforts to respond promptly to project-related communication.

A response may consist of:

- approval
- objection
- request for clarification/information

Acknowledgment of receipt is generally preferred over silence, but doesn't constitute a valid response or decision on its own. Response handling is governed by [the inactivity rule.](#32-inactivity-rule)

---

# 3. Decision-Making Process

## 3.1 Standard Process

Any team member may propose:

- a design decision
- architecture change
- database modification
- workflow adjustment
- implementation approach
- requirement interpretation

Any other member may, in response:

- approve
- object
- suggest modifications
- provide an alternative proposal

Any/all of which must be communicated through an agreed-upon channel.

---

## 3.2 Inactivity Rule

Should the following ever transpire:

1. a project-related proposal has been communicated;
2. project progress is blocked pending a response; and
3. no substantive response is received within **six (6) hours**;

__The proposing member may proceed (act and/or decide) on behalf of the team.__

Any resulting decision shall be considered an official project decision unless superseded later through formal, mutual, written agreement.

__Project progress will not remain indefinitely blocked awaiting input.__

---

## 3.3 Active Disagreement

An inactive or non-responsive member shall not be considered to have disagreed. __Silence is not disagreement.__

Should two or more members actively disagree on a decision:

- discussion should continue in good faith (see [section 1](#1-purpose));
- all involved parties should present technical or project-based justification; and
- neither member may claim unilateral authority solely because agreement has not yet been reached.

---

# 4. Task Ownership

## 4.1 Ownership

Any team member who formally claims (via GH Issues or elsewhere in writing) and begins work on a specific task becomes the temporary owner of that task.

Examples include:

- bug fixes
- database changes
- feature implementations
- testing efforts
- documentation

Each team member may only claim a maximum of three (3) adjacent issues/tasks simultaneously. This may only be extended to fully-encapsulating parent issues. Examples:

- if a parent issue has four sub-issues, any one team member can claim up to three of those sub-issues, but not the parent issue.
- if a parent issue has three sub-issues, any one team member can claim up to all three of those sub-issues, plus the parent issue itself.

Ownership and task assignment protocol exists to prevent duplicate effort and improve accountability.

---

## 4.2 Abandoned Work

If work appears stalled and no meaningful status update through any approved channel has been provided for at least **six (6) hours**, any member may assume responsibility for completing, revising, or replacing that work.

---

# 5. Git Workflow

## 5.1 Branches

Team members should perform development work within appropriately-labeled feature or bug-fix branches.

Examples:

- `feature/customer-dao`
- `feature/account-notes`
- `feature/employee/rev1`
- `bugfix/transaction-history`

---

## 5.2 Pull Requests (PRs)

PRs should:

- summarize implemented changes both/either in:
    - main body
    - code/diff comments
- identify relevant issues or user stories
- describe any known limitations
- provide sufficient information for review
- be assigned relevant label, task type, project board, and milestone

---

## 5.3 Review Window

PRs should be reviewed as promptly as practical.

Should the following transpire:

1. PR is opened
2. review is requested
3. no approval, change requests, or questions are received within **six (6) hours**

Its author may commit any/all suggested changes and merge the PR.

---

## 5.4 Merge Blocking

A Pull Request should be blocked only for a documented technical concern such as:

- incorrect behavior;
- failed requirements;
- security concerns;
- merge conflicts; or
- significant maintainability concerns.

_Failure to review shall not, by itself, block project progress._

---

# 6. Code Review Expectations

Code reviews should focus primarily on, in descending order of importance:

- correctness
- requirements compliance
- project standards
- testability
- maintainability
- readability

Review comments and suggested changes should reference (be created directly on) specific lines of code.

Comment and suggestion handling is subject to the Review Window guidelines.

---

# 7. Documentation of Decisions

Major project decisions should be documented through one of the following:

- GitHub Issues
- GitHub Discussions
- PR comments
- project documentation
- meeting notes

Purpose of documentation is to:

- create a visible project history
- reduce repeated discussions
- provide context for future work

Team agrees that Discord and verbal/video communications are neither suitable nor acceptable repositories for documentation.

---

# 8. Delivery Period Authority

Team acknowledges that impending project deadlines will require rapid decision-making.

Should the following transpire:

1. action is required to maintain project progress
2. a proposal has been communicated
3. the relevant response period has elapsed
4. no substantive objection has been received

Any proposing team member is authorized to act and decide on behalf of the team.

The intent of this provision is to prevent project stagnation and ensure continued progress toward delivery.

---

# 9. Good Faith Clause

All team members agree to:

- act in the project's best interests
- communicate honestly and professionally
- avoid unnecessary delays
- document significant decisions where practical
- assume good faith in each other's actions

Authority granted by this agreement is intended solely to facilitate project progress and shall not be used to intentionally exclude, undermine, or disadvantage another team member.

---

# 10. Acceptance

Approval of this document signifies agreement to operate according to the expectations and procedures described within for the remainder of the project.

## Member Approval

Approval of this document is to be furnished by unanimous approval of the PR in which it is presented. Each subsequent revision requires such approval in order to functionally supersede its predecessor. No handwritten signature is required.