'use strict';
import {NativeModules, Platform} from 'react-native';

const {ThermometryModule} = NativeModules;

export {
    ThermometryModule.start,//启动测温，无入参
    ThermometryModule.readTemperature,//返回体表温度，无入参
    ThermometryModule.stop,//停止测温，无入参
};