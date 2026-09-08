# University Timetable Scheduler

A Java program that generates a valid, optimized university course timetable using constraint satisfaction and local search optimization.

## Problem

Universities need to schedule courses into timeslots such that no lecturer or student is double-booked, while also producing a well-distributed timetable (avoiding all courses being clustered on the same day). This project models that problem and solves it in two stages: finding *any* valid timetable, then improving its quality.

## Constraints Modeled

**Hard constraints (must never be violated):**
- No lecturer is scheduled to teach two courses in the same timeslot.
- No student has two of their enrolled courses in the same timeslot.
- No timeslot is assigned to more than one course.
- A course's enrolled student count must not exceed its room capacity.

**Soft constraint (optimized, not required):**
- Courses should be spread evenly across the week rather than clustered on a single day.

**Assumption/scope note:** this model does not represent physical room allocation across departments (i.e. it assumes no shared-room contention beyond the "one course per slot" rule above). 

**1. Backtracking (Constraint Satisfaction Search)**
Recursively assigns each course to a timeslot, checking all hard constraints before committing. If a course has no valid remaining option, the algorithm backtracks and tries a different earlier assignment. This guarantees a valid timetable is found if one exists.

**2. Steepest Ascent Hill Climbing (Local Search Optimisation)**
Once a valid timetable is found, this stage improves its quality. Each iteration, every possible single-course reassignment is evaluated, and the move producing the greatest score improvement is applied. This repeats until no further improvement is possible (a local/global optimum is reached).

An earlier design used a pairwise-swap neighborhood (trading two courses' timeslots), but this failed when all courses started on the same day, since no course existed on other days to swap with. Switching to single-course reassignment (moving one course to any timeslot, occupied or empty) resolved this.

## Scoring Function

Timetables are scored by how unevenly courses are distributed across days — lower is better, 0 is a perfectly even spread. The penalty for an overloaded day is squared (rather than linear) to penalize heavy clustering more than mild unevenness.

## Example Result

With [9] courses, [5] lecturers, and [8] students (with overlapping enrollments):

- Score before optimization: [25]
- Score after optimization: [0]

**Before (unoptimised):**
Timetable found:
Cybersecurity --> TUE P1
Software Engineering --> MON P1
Networks --> MON P2
Databases --> MON P2
Algorithms --> MON P1
Machine Learning --> MON P3
AI Fundamentals --> MON P1
Operating Systems --> MON P2
Web Development --> MON P1

**After (optimised):**
Optimised timetable:
Cybersecurity --> TUE P1
Software Engineering --> TUE P1
Networks --> TUE P2
Databases --> WED P1
Algorithms --> WED P2
Machine Learning --> WED P1
AI Fundamentals --> MON P1
Operating Systems --> MON P2
Web Development --> MON P1

**In grid format**
Final optimised timetable: 
        MON                   TUE                   WED                   
P1      Web Development       Software Engineering  Machine Learning      
P2      Operating Systems     Networks              Algorithms            
P3      -                     -                     -                     
