from config import Conf
import subprocess


def generate_wave(midi_file, wave_file):
    conf = Conf()
    cmd_args = [conf.MUSESCORE_BIN, "-o", wave_file, midi_file]
    subprocess.run(cmd_args)


def generate_wave_command(midi_file, wave_file):
    conf = Conf()
    cmd_args = ['"'+conf.MUSESCORE_BIN+'"', "-o", wave_file, midi_file]
    return " ".join(cmd_args)
