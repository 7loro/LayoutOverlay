## Overview
Make a overlay window to show transparent shape to compare size with view, margin.

## Features
- Overlay window
    - Drag
    - Drag and drop to remove
    - Double click to open Activity
- Various shape
    - Rectangular
    - Square
    - Circle
- Custom size, color, transparency
- Multiple overlay

## Architecture
- Clean architecture
    - Multi module
    - app
        - Activity for navigation
    - feature
        - overlay
            - presentation
                - Service
                    - Service to draw overlay window
            - domain
            - data
        - home
            - presentation
            - domain
            - data

## Dependency
- KTS
- Gradle Version Catalog

## Screenshot
