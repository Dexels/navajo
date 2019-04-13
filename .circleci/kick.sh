#!/bin/sh
curl -u ${CIRCLE_TOKEN}: \
     -d build_parameters[CIRCLE_J:OB]=deploy_docker \
     https://circleci.com/api/v1.1/project/<vcs-type>/<org>/<repo>/tree/<branch>

