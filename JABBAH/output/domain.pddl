(define (domain midominio)

(:requirements
:typing 
:fluents 
:htn-expansion 
:durative-actions 
:negative-preconditions
:universal-preconditions
:disjuntive-preconditions
:derived-predicates
:metatags)


(:types
parameter - object
activity - object
participant - object
lane - object
boolean - object
)

(:constants
 true false - boolean
a0 a2 a4 a5 a6 a9 a8 a11 a12 a14 a15 a17 a19 a20 a1 - activity
Optimize - parameter
Training Authoring Formatting GraphicDesign Administration Quality  - lane
)

(:predicates
(completed ?a - activity)
(belongs_to_lane ?p - participant ?a - lane)
(value ?x - parameter ?v - boolean)
)


(:durative-action A0
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w Training)
:effect (completed a0))

(:durative-action A1
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w Administration)
:effect (completed a1))

(:durative-action A2
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Training)
:effect (completed a2))

(:durative-action A4
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Training)
:effect (completed a4))

(:durative-action A5
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Authoring)
:effect (completed a5))

(:durative-action A6
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Formatting)
:effect (completed a6))

(:durative-action A8
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed a8))

(:durative-action A9
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed a9))

(:durative-action A11
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed a11))

(:durative-action A12
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w GraphicDesign)
:effect (completed a12))

(:durative-action A14
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Authoring)
:effect (completed a14))

(:durative-action A15
:parameters(?w - participant)
:duration (= ?duration 10.0)
:condition(belongs_to_lane ?w Quality)
:effect (completed a15))

(:durative-action A17
:parameters(?w - participant)
:duration (= ?duration 20.0)
:condition(belongs_to_lane ?w Formatting)
:effect (completed a17))

(:durative-action A19
:parameters(?w - participant)
:duration (= ?duration 5.0)
:condition(belongs_to_lane ?w Administration)
:effect (completed a19))

(:durative-action A20
:parameters(?w - participant)
:duration (= ?duration 5.0)
:condition(belongs_to_lane ?w Administration)
:effect (completed a20))
(:task BlockSB2
:parameters ()
(:method blsb2
:precondition ()
:tasks ((A0 ?w1) (A2 ?w2) (BlockPB3) (A19 ?w3) (A20 ?w4) (A1 ?w5) )
))

(:task BlockSB1
:parameters ()
(:method blsb1
:precondition ()
:tasks ((A5 ?w1) (A6 ?w2) (BlockPB1 Optimize) (A11 ?w3) (A12 ?w4) (BlockPB2) (A17 ?w5) )
))

(:task BlockPB3
:parameters ()
(:method blpb3
:precondition ()
:tasks ([(A4 ?w1) (BlockSB1) ](A19 ?w2))
))

(:task BlockPB1
:parameters (?x - parameter)
(:method if_A9
:precondition (value ?x false)
:tasks (A9?w1))
(:method if_A8
:precondition (value ?x true)
:tasks (A8?w1))
)

(:task BlockPB2
:parameters ()
(:method blpb2
:precondition ()
:tasks ([(A14 ?w1) (A15 ?w2) ](A17 ?w3))
))


)