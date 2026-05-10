---
name: add-or-update-license-files
description: Workflow command scaffold for add-or-update-license-files in jabbah.
allowed_tools: ["Bash", "Read", "Write", "Grep", "Glob"]
---

# /add-or-update-license-files

Use this workflow when working on **add-or-update-license-files** in `jabbah`.

## Goal

Adds or updates license and copyright files, ensuring all source files have correct license headers.

## Common Files

- `JABBAH/src/jabbah/license/*`
- `JABBAH/src/jabbah/*.java`

## Suggested Sequence

1. Understand the current state and failure mode before editing.
2. Make the smallest coherent change that satisfies the workflow goal.
3. Run the most relevant verification for touched files.
4. Summarize what changed and what still needs review.

## Typical Commit Signals

- Add or update license files in license directory
- Add or update license comments in each source file

## Notes

- Treat this as a scaffold, not a hard-coded script.
- Update the command if the workflow evolves materially.