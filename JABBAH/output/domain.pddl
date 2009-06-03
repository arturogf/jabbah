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
boolean - object
activity - object
gateway - object
participant - object
role - object
lane - object)

(:constants
 true false - boolean
s a1 a3 a2 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 e - activity
optimize - parameter
)

(:predicates
(completed ?a - activity)
(belongs_to_lane ?p - participant ?a - lane)
(igual ?x - parameter ?y - boolean))


(:durative-action S
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed s))

(:durative-action A1
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a1))

(:durative-action A3
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a3))

(:durative-action A2
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a2))

(:durative-action A4
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a4))

(:durative-action A5
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a5))

(:durative-action A6
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a6))

(:durative-action A7
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a7))

(:durative-action A8
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a8))

(:durative-action A9
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a9))

(:durative-action A10
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a10))

(:durative-action A11
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a11))

(:durative-action A12
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a12))

(:durative-action A13
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed a13))

(:durative-action E
:parameters(?w - participant)
:duration (= ?duration 1.0)
:condition(belongs_to_lane ?w LANE_NAME)
:effect (completed e))
(:task BlockSB2
:parameters ()
(:method blsb2
:precondition ()
:tasks ((S ?w1) (A1 ?w2) (BlockPB3) (A12 ?w3) (A13 ?w4) (E ?w5) )
))

(:task BlockSB1
:parameters ()
(:method blsb1
:precondition ()
:tasks ((A2 ?w1) (A4 ?w2) (BlockPB1 optimize) (A7 ?w3) (A8 ?w4) (BlockPB2) (A11 ?w5) )
))

(:task BlockPB3
:parameters ()
(:method blpb3
:precondition ()
:tasks ([(A3 ?w1) (BlockSB1) ](A12 ?w2))
))

(:task BlockPB1
:parameters (?x - parameter)
(:task BlockPB2
:parameters ()
(:method blpb2
:precondition ()
:tasks ([(A9 ?w1) (A10 ?w2) ](A11 ?w3))
))

