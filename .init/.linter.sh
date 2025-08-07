#!/bin/bash
cd /home/kavia/workspace/code-generation/simple-notes-manager-147416-147443/notes_app_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

