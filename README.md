# Advanced Java Projects Portfolio

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> **A collection of advanced Java applications demonstrating data structures, multimedia processing, and algorithmic problem-solving.**

## Featured Projects

### 1. **Dictionary System** (`Dictionary.java`)
A comprehensive Spanish-English dictionary implementation featuring:
- **Hash-based lookups** for O(1) average-case retrieval
- Support for 10,000+ vocabulary entries
- Efficient memory management for large datasets
- Extensible architecture for additional languages

**Technical Highlights:**
- Custom data structure implementation
- Optimized string matching algorithms
- Real-world language processing application

---

### 2. **Video Processing Suite** (`TimedTests/`)

#### **VideoMerger** - Advanced Frame Extraction & Processing
Integrates with FFmpeg to extract and process video frames with precision.

**Features:**
- Frame-by-frame video analysis
- Batch processing of video content
- Custom image dimension detection
- Automated frame extraction pipeline

**Use Cases:**
- Video analysis and annotation
- Computer vision preprocessing
- Media content analysis

#### **AudioTiming** - Synchronization & Analysis
Precise audio timing and synchronization tools for multimedia applications.

**Capabilities:**
- Audio file parsing and analysis
- Timing precision measurement
- Synchronization algorithms

#### **AverageLightVideo** - Video Luminance Analysis
Analyzes video content for average brightness and light distribution.

**Applications:**
- Video quality assessment
- Exposure analysis
- Content normalization

---

### 3. **Mathematical Equation Solver** (`TimedTests/EquationSolver.java`)

A sophisticated system solver for linear equations with GUI interface.

**Key Features:**
- **Matrix operations** - Gaussian elimination with partial pivoting
- **GUI Interface** - User-friendly Swing-based interface
- **Permutation generation** - Explores solution spaces systematically
- **Performance metrics** - Real-time execution timing

**Algorithm Highlights:**
```
• Matrix decomposition and solving
• Systematic solution space exploration
• Optimized iteration through permutations
• Real-time performance measurement
```

**Technical Complexity:**
- Handles systems up to nth order
- Efficient backtracking algorithms
- Custom matrix manipulation
- Big-O optimized for large systems

---

### 4. **Computer System Scanner** (`TimedTests/ScanComputer.java`)

**WARNING: Educational Tool Only**

A system exploration tool demonstrating:
- File system traversal algorithms
- Directory structure analysis
- System resource enumeration

**Learning Objectives:**
- Understanding filesystem APIs
- Recursive directory traversal
- System programming concepts

---

## Getting Started

### Prerequisites
```bash
Java Development Kit (JDK) 11 or higher
FFmpeg (for video processing tools)
```

### Quick Start

#### Compile and Run Dictionary:
```bash
javac Dictionary.java
java Dictionary
```

#### Compile and Run Equation Solver:
```bash
javac TimedTests/EquationSolver.java
java -cp TimedTests EquationSolver
```

#### Video Processing:
```bash
# Update paths in VideoMerger.java first
javac TimedTests/VideoMerger.java
java -cp TimedTests VideoMerger
```

---

## Project Statistics

| Project | Lines of Code | Complexity | Domain |
|---------|--------------|------------|---------|
| Dictionary | ~8,500 | Medium | Data Structures |
| VideoMerger | ~150 | High | Multimedia |
| EquationSolver | ~200 | High | Mathematics |
| AudioTiming | ~100 | Medium | Signal Processing |

---

## Technical Stack

- **Language:** Java 11+
- **GUI:** Swing Framework
- **Media Processing:** FFmpeg integration
- **Data Structures:** Custom implementations (HashMaps, Arrays, Lists)
- **Algorithms:** Gaussian elimination, permutation generation, recursive traversal

---

## Project Structure

```
Java/
├── Dictionary.java           # 8500+ line dictionary implementation
├── file_creation.java        # File I/O utilities
├── TimedTests/              # Performance & multimedia suite
│   ├── EquationSolver.java  # Matrix equation solver with GUI
│   ├── VideoMerger.java     # Video frame extraction
│   ├── AudioTiming.java     # Audio synchronization
│   ├── AverageLightVideo.java # Luminance analysis
│   ├── ScanComputer.java    # System traversal (educational)
│   └── ScanComputerMain.java
└── README.md
```

---

## Learning Outcomes

This repository demonstrates proficiency in:

1. **Data Structures & Algorithms**
   - Custom implementations
   - Optimization techniques
   - Complexity analysis

2. **Multimedia Processing**
   - Video frame manipulation
   - Audio timing and sync
   - FFmpeg integration

3. **Mathematical Computing**
   - Linear algebra implementations
   - Matrix operations
   - Numerical methods

4. **System Programming**
   - File I/O operations
   - System resource access
   - Cross-platform compatibility

5. **Software Engineering**
   - Clean code practices
   - Modular architecture
   - Documentation standards

---

## License

MIT License - See [LICENSE](LICENSE) file for details

---

## Author

**Andrey Vasilyev**

*Developed during 8th grade (June-July 2023) as part of advanced computer science self-study*

---

## Future Enhancements

- [ ] Migrate to Maven/Gradle build system
- [ ] Add comprehensive unit tests
- [ ] Implement CI/CD pipeline
- [ ] Add real-time video processing
- [ ] Expand dictionary to support multiple languages
- [ ] Create web API for equation solver

---

## Notes

- Video processing tools require FFmpeg installation
- Some paths are machine-specific and need configuration
- Computer scanning tools are for educational purposes only
- All projects are standalone and can be compiled individually

---


