# Audio Feature Extraction & Machine Learning Pipeline 🎙️📊

A concise, self-contained Python project demonstrating audio signal processing, feature extraction, hierarchical clustering, and classification performance evaluation.

---

## 📌 Project Overview

This repository contains three core Python scripts that form a complete audio analysis and modeling workflow. It covers everything from extracting spectral representations of audio files to clustering structure discovery and model evaluation metrics.

Designed for modular usage, this project works out-of-the-box with any standard `.wav` audio dataset.

---

## 📂 Repository Structure

```text
.
├── audio_feature_extraction.py   # Extracts Mel-Spectrograms & normalizes audio vectors
├── hierarchical_clustering.py     # Performs distance matrix & dendrogram clustering
├── model_evaluation.py            # Evaluates neural network/classifier outputs & confusion matrices
└── README.md                      # Project documentation
```

### Module Descriptions

1. **`audio_feature_extraction.py`**
   - Loads audio tracks using `librosa`.
   - Computes Mel-Frequency Spectrograms (MFS) across variable-length recordings.
   - Applies zero-padding to standardize vector dimensions for machine learning algorithms.

2. **`hierarchical_clustering.py`**
   - Calculates pairwise distance matrices across feature vectors.
   - Constructs hierarchical linkage trees using `scipy.cluster.hierarchy`.
   - Generates visual dendrograms to analyze audio signal groupings (e.g., speech audio vs. synthetic/spoof audio).

3. **`model_evaluation.py`**
   - Takes raw neural network outputs or classifier predictions.
   - Computes confusion matrices, accuracy, precision, recall, and F1-scores.
   - Plots clean performance visualizations using `matplotlib` and `seaborn`.

---

## 🛠️ Requirements & Installation

Ensure you have Python 3.8 or higher installed. Install all required dependencies using `pip`:

```bash
pip install numpy librosa matplotlib seaborn scipy scikit-learn
```

---

## 🚀 How to Run

You can execute each script independently depending on your workflow stage:

1. **Extract Audio Features:**
   ```bash
   python audio_feature_extraction.py
   ```

2. **Run Hierarchical Clustering:**
   ```bash
   python hierarchical_clustering.py
   ```

3. **Evaluate Classification Metrics:**
   ```bash
   python model_evaluation.py
   ```

---

## 🔬 Key Methodologies

- **Spectral Processing**: Mel-scale spectrogram generation ($128$ mel bands) for robust speech signal representation.
- **Dimensionality Standardisation**: Time-axis feature padding for variable-length acoustic samples.
- **Unsupervised Analysis**: Agglomerative hierarchical clustering via Ward's / complete linkage methods.
- **Evaluation**: Quantitative classification diagnostics for binary and multi-class speech tasks.
