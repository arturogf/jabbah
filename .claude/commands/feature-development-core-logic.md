---
name: feature-development-core-logic
description: Workflow command scaffold for feature-development-core-logic in jabbah.
allowed_tools: ["Bash", "Read", "Write", "Grep", "Glob"]
---

# /feature-development-core-logic

Use this workflow when working on **feature-development-core-logic** in `jabbah`.

## Goal

Implements or updates core logic/features, typically involving multiple related Java source files.

## Common Files

- `JABBAH/src/jabbah/*.java`

## Suggested Sequence

1. Understand the current state and failure mode before editing.
2. Make the smallest coherent change that satisfies the workflow goal.
3. Run the most relevant verification for touched files.
4. Summarize what changed and what still needs review.

## Typical Commit Signals

- Edit or add Java source files in JABBAH/src/jabbah/
- Edit related classes to support the feature
- Update main application logic if needed
- Update or add utility/helper classes

## Notes

- Treat this as a scaffold, not a hard-coded script.
- Update the command if the workflow evolves materially.