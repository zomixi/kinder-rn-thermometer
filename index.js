"use strict";
import { NativeModules } from "react-native";

const { ThermometryModule } = NativeModules;

module.exports = {
  start: ThermometryModule.start, //启动测温，无入参
  stop: ThermometryModule.stop, //停止测温，无入参
  readTemperature: ThermometryModule.readTemperature, //返回体表温度，无入参
};
