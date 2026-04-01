# Mesa KGSL/Turnip Build for Adreno 7xx on PRoot (No-Root)

## 📌 Overview
This repository documents a deep-dive experimental attempt to compile and run Mesa with KGSL patches for Adreno 7xx GPUs inside a PRoot Ubuntu container on Android 14 (Samsung, No-Root). 

The goal was to achieve hardware acceleration.

## 🛠️ Environment Specs
- **Device Setup:** Samsung Galaxy  (Snapdragon processor with Adreno 7xx series GPU)
- **OS Restrictions:** Android 14 (Strict SELinux/Knox enforcement)
- **Linux Environment:** PRoot Ubuntu via Termux

## 🚀 What Works (The Build Process)
- Successfully cloned and patched Mesa (`v24.2.8`) with custom `freedreno-kmds=kgsl` patches.
- Successfully compiled the source code directly on the device using `meson` and `ninja`.
- The Vulkan loader successfully detects the `freedreno_icd.aarch64.json` file.
- The system recognizes `/dev/kgsl-3d0`.

## 🛑 What Fails (The Runtime Roadblock)
Execution fails at the `vkEnumeratePhysicalDevices` stage. Despite a flawless compilation and build process, the system returns:
`Failed to detect any valid GPUs in the current config`

**Conclusion & Takeaway:** The issue is not with the drivers or the build process. Samsung's strict Knox/SELinux policies on Android 14 physically block the PRoot container from communicating directly with the hardware GPU node (`/dev/kgsl-3d0`). Until a bypass for this specific permission restriction is found, true no-root hardware acceleration remains impossible on this setup.
